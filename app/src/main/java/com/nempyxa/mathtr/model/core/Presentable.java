package com.nempyxa.mathtr.model.core;

import android.view.View;

/**
 * Interface that is able to present itself as a string or Android View
 */
public interface Presentable {
    /**
     * Get string presentation of the object
     * @return String presentation of the object
     */
    String stringPresentation();

    /**
     * Get Android View presentation of the object
     * @return View presentation of the object
     */
    View viewPresentation();
}
