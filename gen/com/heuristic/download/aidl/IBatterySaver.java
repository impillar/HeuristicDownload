/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/pillar/workspace/Android/HeuristicDownload/src/com/heuristic/download/aidl/IBatterySaver.aidl
 */
package com.heuristic.download.aidl;
public interface IBatterySaver extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.heuristic.download.aidl.IBatterySaver
{
private static final java.lang.String DESCRIPTOR = "com.heuristic.download.aidl.IBatterySaver";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.heuristic.download.aidl.IBatterySaver interface,
 * generating a proxy if needed.
 */
public static com.heuristic.download.aidl.IBatterySaver asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.heuristic.download.aidl.IBatterySaver))) {
return ((com.heuristic.download.aidl.IBatterySaver)iin);
}
return new com.heuristic.download.aidl.IBatterySaver.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_download:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
long _arg2;
_arg2 = data.readLong();
java.lang.String _arg3;
_arg3 = data.readString();
java.lang.CharSequence _result = this.download(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
android.text.TextUtils.writeToParcel(_result, reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_upload:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
long _arg2;
_arg2 = data.readLong();
java.lang.String _arg3;
_arg3 = data.readString();
java.lang.CharSequence _result = this.upload(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
android.text.TextUtils.writeToParcel(_result, reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_getProgress:
{
data.enforceInterface(DESCRIPTOR);
java.lang.CharSequence _arg0;
if ((0!=data.readInt())) {
_arg0 = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(data);
}
else {
_arg0 = null;
}
double _result = this.getProgress(_arg0);
reply.writeNoException();
reply.writeDouble(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.heuristic.download.aidl.IBatterySaver
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
	 * To download a file in a specific url, and store it in a specific folder
	 * The download process needs to be limited in "duration" seconds
	 * @param appId
	 * @param url
	 * @param duration
	 * @param file
	 * @return UUID, a unique identifier for the download request.
	 */
@Override public java.lang.CharSequence download(java.lang.String appId, java.lang.String url, long duration, java.lang.String folder) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.CharSequence _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(appId);
_data.writeString(url);
_data.writeLong(duration);
_data.writeString(folder);
mRemote.transact(Stub.TRANSACTION_download, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * To upload a file to a specific url.
	 * @param appId
	 * @param url
	 * @param duration
	 * @param file
	 * @return UUID, a unique identifier for the upload request.
	 */
@Override public java.lang.CharSequence upload(java.lang.String appId, java.lang.String url, long duration, java.lang.String file) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.CharSequence _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(appId);
_data.writeString(url);
_data.writeLong(duration);
_data.writeString(file);
mRemote.transact(Stub.TRANSACTION_upload, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = android.text.TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	 * Users can query the current progress of requested download, by specifying the UUID of the request
	 * @param uuid
	 * @return
	 */
@Override public double getProgress(java.lang.CharSequence uuid) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
double _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
if ((uuid!=null)) {
_data.writeInt(1);
android.text.TextUtils.writeToParcel(uuid, _data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_getProgress, _data, _reply, 0);
_reply.readException();
_result = _reply.readDouble();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_download = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_upload = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getProgress = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
/**
	 * To download a file in a specific url, and store it in a specific folder
	 * The download process needs to be limited in "duration" seconds
	 * @param appId
	 * @param url
	 * @param duration
	 * @param file
	 * @return UUID, a unique identifier for the download request.
	 */
public java.lang.CharSequence download(java.lang.String appId, java.lang.String url, long duration, java.lang.String folder) throws android.os.RemoteException;
/**
	 * To upload a file to a specific url.
	 * @param appId
	 * @param url
	 * @param duration
	 * @param file
	 * @return UUID, a unique identifier for the upload request.
	 */
public java.lang.CharSequence upload(java.lang.String appId, java.lang.String url, long duration, java.lang.String file) throws android.os.RemoteException;
/**
	 * Users can query the current progress of requested download, by specifying the UUID of the request
	 * @param uuid
	 * @return
	 */
public double getProgress(java.lang.CharSequence uuid) throws android.os.RemoteException;
}
