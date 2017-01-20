// IBookManager.aidl
package tk.dongye.advanceapp.bean;

import tk.dongye.advanceapp.bean.Book;
// Declare any non-default types here with import statements

interface IBookManager {
    List<Book> getBookList();
    void addBook(in Book book);
}
