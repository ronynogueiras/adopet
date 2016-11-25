package br.ufg.inf.adopet.util;

import android.app.Dialog;

/**
 * Created by rony on 25/11/16.
 */

public interface UserDialog {
    void showDialogToUser(String[] args, Dialog.OnClickListener positiveAction, Dialog.OnClickListener negativeAction);
}
