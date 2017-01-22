// IBookManager.aidl
package tk.dongye.advanceapp.aidl;

import tk.dongye.advanceapp.aidl.Book;
// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
