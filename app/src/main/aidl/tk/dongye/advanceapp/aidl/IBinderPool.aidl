// IBinderPool.aidl
package tk.dongye.advanceapp.aidl;

// Declare any non-default types here with import statements

interface IBinderPool {
   IBinder queryBinder(int binderCode);
}
