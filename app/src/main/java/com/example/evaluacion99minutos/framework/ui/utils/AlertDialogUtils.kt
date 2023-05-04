package com.example.evaluacion99minutos.framework.ui.utils

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AlertDialog
import com.example.evaluacion99minutos.R

/**
 * AlertParams
 *
 * @param context context instance
 * @param title alert title
 * @param message alert message
 * @param acceptTitle accept title button
 * @param onAccept accept action of alert button
 * @param cancelTitle cancel title button
 * @param onCancel cancel action of alert button
 * @param onCloseAction close action of alert icon
 * @param icon icon of custom alert
 * @param linkMessage message link
 * @param linkWord word to be link
 * @param linkColor color link for link word in message
 */
data class AlertParams(
    val context: Context,
    var title: String?,
    var message: String?,
    var acceptTitle: String?,
    var onAccept: (() -> Unit?)?,
    var cancelTitle: String?,
    var onCancel: (() -> Unit?)?,
    var onCloseAction: (() -> Unit?)?,
)

/**
 * AlertDialogUtils
 * Created by pablogutierrez on 31/12/18.
 *
 * @param alertParams alert params
 */
class AlertDialogUtils private constructor(val alertParams: AlertParams) {

    /**
     * Method to show alert dialog
     */
    fun showAlert() {
        val alertBuilder = AlertDialog.Builder(ContextThemeWrapper(alertParams.context, R.style.Theme_Evaluacion99Minutos))
        alertBuilder.setTitle(alertParams.title)
        alertBuilder.setMessage(alertParams.message)
        if (alertParams.onAccept == null) {
            alertBuilder.setPositiveButton(alertParams.acceptTitle) { dialogInterface, _ -> dialogInterface.dismiss() }
        } else {
            alertBuilder.setPositiveButton(alertParams.acceptTitle) { _, _ -> alertParams.onAccept!!.invoke() }
        }

        if (alertParams.onCancel != null) {
            alertBuilder.setNegativeButton(alertParams.cancelTitle) { _, _ -> alertParams.onCancel!!.invoke() }
        }
        alertBuilder.setCancelable(false)
        alertBuilder.create().show()
    }

    /**
     * Builder class of alert dialog
     *
     * @param context context instance
     */
    class Builder(private val context: Context) {

        /**
         * Title of alert dialog
         */
        private var mTitle: String? = ""
        /**
         * Icon of custom alert dialog
         */
        private var mIcon: Int = -1
        /**
         * Message of alert dialog
         */
        private var mMessage: String? = ""
        /**
         * Accept title button
         */
        private var mAcceptTitle: String? = ""
        /**
         * String for text of link
         */
        private var mLink: String? = ""
        /**
         * Accept action of alert button
         */
        private var mOnAcceptAction: (() -> Unit?)? = null
        /**
         * Cancel title button
         */
        private var mCancelTitle: String? = ""
        /**
         * Cancel action of alert button
         */
        private var mOnCancelAction: (() -> Unit?)? = null
        /**
         * Close action of alert icon
         */
        private var mOnCloseAction: (() -> Unit?)? = null
        /**
         * Link action of alert
         */
        private var mOnLinkAction: (() -> Unit?)? = null
        /**
         * String message to build a link inside
         */
        private var mMessageWithLink: String? = ""
        /**
         * Word with link
         */
        private var mLinkWord: String? = ""

        /**
         * Word link color
         */
        private var mLinkColor = -1

        /**
         * Method to set title of alert dialog
         *
         * @param title title of alert dialog
         *
         * @return this
         */
        fun setTitle(title: String): Builder {
            this.mTitle = title
            return this
        }

        /**
         * Method to set icon of alert dialog
         *
         * @param icon icon of custom alert dialog
         *
         * @return this
         */
        fun setIcon(icon: Int): Builder {
            this.mIcon = icon
            return this
        }

        /**
         * Method to set message of alert dialog
         *
         * @param message message of alert dialog
         *
         * @return this
         */
        fun setMessage(message: String): Builder {
            this.mMessage = message
            return this
        }

        /**
         * Method to set accept button
         *
         * @param title accept title button
         * @param onAccept accept action of alert button
         *
         * @return this
         */
        fun setAccept(title: String, onAccept: () -> Unit?): Builder {
            this.mAcceptTitle = title
            this.mOnAcceptAction = onAccept
            return this
        }

        /**
         * Method to set cancel button
         *
         * @param title cancel title button
         * @param onCancel cancel action of alert button
         *
         * @return this
         */
        fun setCancel(title: String, onCancel: () -> Unit?): Builder {
            this.mCancelTitle = title
            this.mOnCancelAction = onCancel
            return this
        }

        /**
         * Method to set close icon action of custom alert dialog
         *
         * @param onClose close action of alert icon
         *
         * @return this
         */
        fun setCloseAction(onClose: () -> Unit?): Builder {
            this.mOnCloseAction = onClose
            return this
        }

        /**
         * Method to show alert dialog
         */
        fun showAlert() {
            val alertParams =
                AlertParams(context, mTitle, mMessage, mAcceptTitle, mOnAcceptAction, mCancelTitle,
                    mOnCancelAction, mOnCloseAction)
            AlertDialogUtils(alertParams).showAlert()
        }

        /**
         * Method to build alert dialog
         *
         * @return alert dialog instance
         */
        fun build(): AlertDialogUtils {
            val alertParams =
                AlertParams(context, mTitle, mMessage, mAcceptTitle, mOnAcceptAction, mCancelTitle,
                    mOnCancelAction, mOnCloseAction)
            return AlertDialogUtils(alertParams)
        }
    }

}