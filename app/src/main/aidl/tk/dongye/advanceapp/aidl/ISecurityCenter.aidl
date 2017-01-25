// ISecurityCenter2.aidl
package tk.dongye.advanceapp.aidl;

// Declare any non-default types here with import statements

interface ISecurityCenter {
   String encrypt(String content);
   String decrypt(String password);
}