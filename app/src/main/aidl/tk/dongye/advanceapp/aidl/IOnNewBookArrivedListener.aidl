// IOnNewBookArivedListener.aidl
package tk.dongye.advanceapp.aidl;

import tk.dongye.advanceapp.aidl.Book;

interface IOnNewBookArrivedListener {
    void onNewBookArrived(in Book book);
}
