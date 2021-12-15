package com.nempyxa.mathtr.model;

import com.nempyxa.mathtr.exception.QuestionGenerationException;
import com.nempyxa.mathtr.log.Logger;
import com.nempyxa.mathtr.model.core.Question;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import lombok.Getter;

/**
 * Asynchronous question loading
 */
public class QuestionProvider {

    public enum State {
        NOT_READY, // Provider is not ready to be queried
        READY,     // Provider has preloaded given number of questions and ready to be queried
        EMPTY      // Provider is empty
    }

    private static final int QUEUE_SIZE = 100;
    // When queue size is less than QUEUE_SIZE - LOAD_TRIGGER_THRESHOLD new questions will be loaded
    // to the queue
    private static final int LOAD_TRIGGER_THRESHOLD = 10;
    private final QuestionSource mQuestionSource;

    private StateListener mListener;

    @Getter
    private State mState;
    private boolean mIsSourceEmpty;
    private QuestionAsyncLoader mAsyncLoader;

    private BlockingQueue<Question> mQuestionQueue;

    /**
     * Create QuestionProvider on top of given QuestionSource
     *
     * @param source QuestionSource to load questions from
     */
    public QuestionProvider(QuestionSource source) {
        if (source == null) {
            throw new IllegalArgumentException("Empty source is not allowed");
        }
        this.mQuestionSource = source;
        changeState(State.NOT_READY);
    }

    /**
     * Initialize provider and invoke listener when ready.
     *
     * @param listener Listener will be invoked when provider is ready.
     */
    public void init(StateListener listener) {
        this.mListener = listener;
        initInternal();
    }

    /**
     * Initialize provider with given number of questions to preload.
     *
     * @param preloadNumber Number of question to preload
     * @param listener      Listener will be invoked when given number of questions has been preloaded.
     */
    public void init(int preloadNumber, StateListener listener) {
        // TODO: implement
        throw new UnsupportedOperationException();
    }

    private void initInternal() {
        if (mQuestionSource.size() == 0) {
            mIsSourceEmpty = true;
            changeState(State.EMPTY);
            return;
        }
        mQuestionQueue = new LinkedBlockingQueue<>(QUEUE_SIZE);
        mAsyncLoader = new QuestionAsyncLoader(mQuestionSource, mQuestionQueue);
        mAsyncLoader.load(QUEUE_SIZE, () -> changeState(State.READY), () -> mIsSourceEmpty = true);
    }

    /**
     * Get next question
     *
     * @return Question Question instance or null if provider is empty
     */
    public Question getNext() {
        if (mState == State.NOT_READY) {
            throw new IllegalStateException("Provider is not ready");
        }
        if (mState == State.EMPTY) {
            throw new IllegalStateException("Provider is empty");
        }
        Question result = mQuestionQueue.poll();
        if (result == null) {
            // queue is empty = provider is empty
            changeState(State.EMPTY);
        }
        if (mQuestionQueue.size() < QUEUE_SIZE - LOAD_TRIGGER_THRESHOLD) {
            Logger.d("Queue size threshold - add new question to queue");
            if (!mIsSourceEmpty) {
                mAsyncLoader.load(LOAD_TRIGGER_THRESHOLD, null, () -> mIsSourceEmpty = true);
            }
        }
        return result;
    }

    private void changeState(State state) {
        mState = state;
        if (mListener != null) {
            mListener.onStateChanged(mState);
        }
    }

    public int getSourceSize() {
        return mQuestionSource.size();
    }

    public int getQueueSize() {
        return mQuestionQueue.size();
    }

    @FunctionalInterface
    public interface StateListener {
        void onStateChanged(State state);
    }

    @FunctionalInterface
    public interface SourceEmptyListener {
        void onSourceEmpty();
    }

    @FunctionalInterface
    public interface LoadCompleteListener {
        void onLoadComplete();
    }

    private static class QuestionAsyncLoader {

        private final QuestionSource mSource;
        private final BlockingQueue<Question> mQueue;

        private final Executor mExecutor;

        public QuestionAsyncLoader(QuestionSource source, BlockingQueue<Question> queue) {
            this.mSource = source;
            this.mQueue = queue;
            this.mExecutor = Executors.newSingleThreadExecutor();
        }

        /**
         * Load number of questions from source to queue
         *
         * @param number Number of question to load
         */
        public void load(int number, LoadCompleteListener loadListener, SourceEmptyListener emptyListener) {
            mExecutor.execute(() -> {
                for (int i = 0; i < number; i++) {
                    Question question = null;
                    try {
                        question = mSource.loadNext();
                    } catch (QuestionGenerationException qge) {
                        // Ignore
                    }
                    if (question == null) {
                        // source is empty
                        if (emptyListener != null) {
                            emptyListener.onSourceEmpty();
                        }
                        break;
                    } else {
                        mQueue.offer(question);
                        Logger.d("Adding question to queue: " + question.stringPresentation());
                    }
                }
                if (loadListener != null) {
                    loadListener.onLoadComplete();
                }
            });
        }
    }
}
