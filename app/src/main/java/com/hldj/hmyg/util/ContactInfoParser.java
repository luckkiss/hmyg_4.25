package com.hldj.hmyg.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.Keep;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 通讯录解析类
 */

@Keep
public class ContactInfoParser {

    /**
     * 获取系统全部联系人的api方法
     *
     * @param context
     * @return
     */
    public static List<ContactInfo> findAll(Context context) {
        ContentResolver resolver = context.getContentResolver();
        // 1.查询raw_contacts表，把联系人的id取出来
        Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri datauri = Uri.parse("content://com.android.contacts/data");
        List<ContactInfo> infos = new ArrayList<ContactInfo>();
        Cursor cursor = resolver.query(uri, new String[]{"contact_id"},
                null, null, null);
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            if (id != null) {
                System.out.println("联系人id: " + id);
                ContactInfo info = new ContactInfo();
                info.setId(id);
                // 2.根据联系人的id，查询data表，把这个id的数据取出来
                // 系统api查询data表的时候不是真正的查询的data表，而是查询data表的视图
                Cursor dataCursor = resolver.query(datauri, new String[]{
                                "data1", "mimetype"}, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                        new String[]{id}, null);
                while (dataCursor.moveToNext()) {
                    String data1 = dataCursor.getString(0);
                    String mimetype = dataCursor.getString(1);
                    if ("vnd.android.cursor.item/name".equals(mimetype)) {
                        System.out.println("姓名=" + data1);
                        info.setName(data1);

                    } else if ("vnd.android.cursor.item/email_v2"
                            .equals(mimetype)) {
                        System.out.println("邮箱=" + data1);

                    } else if ("vnd.android.cursor.item/phone_v2".equals(data1)) {
                        System.out.println("电话=" + data1);
                        info.setPhone(data1);
                    } else if ("vnd.android.cursor.item/im".equals(mimetype)) {
                        System.out.println("QQ=" + data1);

                    }

                }
                infos.add(info);
                System.out.println("-------");
                dataCursor.close();
            }
        }
        cursor.close();
        return infos;

    }

    @Keep
    public static class ContactInfo {
        private String name;
        private String id;
        private String phone;
        //        private String email;
//        private String qq;
//        private String headImage;
        private List<String> mobileArray = new ArrayList<>();

        @Override
        public String toString() {
            return "ContactInfo{" +
                    "name='" + name + '\'' +
                    ", id='" + id + '\'' +
                    ", phone='" + phone + '\'' +
                    ", mobileArray=" + mobileArray +
                    '}';
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }


        public List<String> getPhones() {
            return mobileArray;
        }

        public void setPhones(List<String> phones) {
            this.mobileArray = phones;
        }
    }


    public static List<ContactInfo> getContacts(Activity mActivity, OnPhoneUpdateListener phoneUpdata) {
        //联系人的Uri，也就是content://com.android.contacts/contacts
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //指定获取_id和display_name两列数据，display_name即为姓名
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
        };
        //根据Uri查询相应的ContentProvider，cursor为获取到的数据集
        Cursor cursor = mActivity.getContentResolver().query(uri, projection, null, null, null);
//        String[] arr = new String[cursor == null ? 1 : cursor.getCount()];
        List<ContactInfo> contactInfos = new ArrayList<>();


        // 查看联系人有多少个号码，如果没有号码，返回0
//        int phoneCount = cursor
//                .getInt(cursor
//                        .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
//
//        if (phoneCount > 0) {
//
//            Long id = cursor.getLong(0);
//            // 获得联系人的电话号码列表
//            Cursor phoneCursor = mActivity.getContentResolver().query(
//                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                    null,
//                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
//                            + "=" + id, null, null);
//
//            if (phoneCursor != null) {
//                if (phoneCursor.moveToFirst()) {
//                    do {
//                        //遍历所有的联系人下面所有的电话号码
//                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                        //使用Toast技术显示获得的号码
////                    Toast.makeText(ContentProviderDemo.this, "联系人电话："+phoneNumber,Toast.LENGTH_LONG).show();
//                        D.i("--------联系人电话------->>>>----" + phoneNumber);
//                    } while (phoneCursor.moveToNext());
//                }
//            }
//
//
//        }


//        int i = 0;
        if (cursor != null && cursor.moveToFirst()) {
            do {

                ContactInfo contactInfo = new ContactInfo();
                Long id = cursor.getLong(0);
                //获取姓名
                String name = cursor.getString(1);

                int num_count = cursor.getInt(2);
                //指定获取NUMBER这一列数据
                String[] phoneProjection = new String[]{
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                };

                contactInfo.id = id + "";
                contactInfo.name = name;
//                printContacts(mActivity, contactInfo.phones);
                contactInfo.mobileArray = getPhones(contactInfo.id, cursor, mActivity, num_count);

//                arr[i] = id + " , 姓名：" + name;


                //根据联系人的ID获取此人的电话号码
//                Cursor phonesCusor = mActivity.getContentResolver().query(
//                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                        phoneProjection,
//                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
//                        null,
//                        null);

                //因为每个联系人可能有多个电话号码，所以需要遍历
//                if (phonesCusor != null && phonesCusor.moveToFirst()) {
//                    do {
//                        String num = phonesCusor.getString(0);
////                        arr[i] += " , 电话号码：" + num;

                if (contactInfo.mobileArray != null && contactInfo.mobileArray.size() > 0) {
                    contactInfo.phone = contactInfo.mobileArray.get(0);
                }

//                    } while (phonesCusor.moveToNext());
//                }

                Log.i("----", "  " + contactInfo.toString());

                phoneUpdata.onUpdata(contactInfo);
                contactInfos.add(contactInfo);
//                i++;
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }

        return contactInfos;
    }


    /*
   * 自定义显示Contacts提供的联系人的方法
   */
    public static void printContacts(Activity mActivity, List<String> phones) {
        //生成ContentResolver对象
        ContentResolver contentResolver = mActivity.getContentResolver();

        // 获得所有的联系人
        /*Cursor cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
         */
        //这段代码和上面代码是等价的，使用两种方式获得联系人的Uri
        Cursor cursor = contentResolver.query(Uri.parse("content://com.android.contacts/contacts"), null, null, null, null);

        // 循环遍历
        if (cursor.moveToFirst()) {

            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            int displayNameColumn = cursor
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

//            getPhones(cursor, idColumn);


        }

    }


    public static List<String> getPhones(String contactId, Cursor cursor, Activity mActivity, int count) {
        List<String> phones = new ArrayList<>();


        if (count < 1) {
            return phones;
        }

        // 获得联系人的ID

        // 获得联系人姓名


        //使用Toast技术显示获得的联系人信息
//                Toast.makeText(mActivity, "联系人姓名：" + displayName, Toast.LENGTH_LONG).show();


        // 查看联系人有多少个号码，如果没有号码，返回0
//        int phoneCount = cursor
//                .getInt(cursor
//                        .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));


        if (count > 0) {
            // 获得联系人的电话号码列表
            Cursor phoneCursor = mActivity.getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                            + "=" + contactId, null, null);
            if (phoneCursor != null && phoneCursor.moveToFirst()) {
                do {
                    //遍历所有的联系人下面所有的电话号码
                    String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    //使用Toast技术显示获得的号码
//                            Toast.makeText(mActivity, "联系人电话：" + phoneNumber, Toast.LENGTH_LONG).show();
                    D.i("-------联系人电话--------->" + phoneNumber);
                    phones.add(phoneNumber);

                } while (phoneCursor.moveToNext());
            }

            if (phoneCursor != null) {
                phoneCursor.close();
            }
        }


        return phones;
    }


    public static interface OnPhoneUpdateListener {
        void onUpdata(ContactInfo contactInfo);

    }
}

