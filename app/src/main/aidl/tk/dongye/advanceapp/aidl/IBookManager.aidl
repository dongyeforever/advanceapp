// IBookManager.aidl
package tk.dongye.advanceapp.aidl;

import tk.dongye.advanceapp.aidl.Book;
import tk.dongye.advanceapp.aidl.IOnNewBookArrivedListener;

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);

    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}
