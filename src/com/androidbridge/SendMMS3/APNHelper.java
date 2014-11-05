package com.androidbridge.SendMMS3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

public class APNHelper {

	public class APN {
	    public String MMSCenterUrl = "";
	    public String MMSPort = "";
	    public String MMSProxy = ""; 
	}
 
	public APNHelper(final Context context) {
	    this.context = context;
	}   

	public List<APN> getMMSApns() {     
	    final Cursor apnCursor = this.context.getContentResolver().query(Uri.withAppendedPath(Carriers.CONTENT_URI, "current"), null, null, null, null);
	    if ( apnCursor == null ) {
	        return Collections.EMPTY_LIST;
	    } else {
	        final List<APN> results = new ArrayList<APN>();         
	        while ( apnCursor.moveToNext() ) {
	            final String type = apnCursor.getString(apnCursor.getColumnIndex(Carriers.TYPE));
	            if ( !TextUtils.isEmpty(type) && ( type.equalsIgnoreCase(PhoneEx.APN_TYPE_ALL) || type.equalsIgnoreCase(PhoneEx.APN_TYPE_MMS) ) ) {
	                final String mmsc = apnCursor.getString(apnCursor.getColumnIndex(Carriers.MMSC));
	                final String mmsProxy = apnCursor.getString(apnCursor.getColumnIndex(Carriers.MMSPROXY));
	                final String port = apnCursor.getString(apnCursor.getColumnIndex(Carriers.MMSPORT));                  
	                final APN apn = new APN();
	                apn.MMSCenterUrl = mmsc;
	                apn.MMSProxy = mmsProxy;
	                apn.MMSPort = port;
	                results.add(apn);
	            }
	        }                   
	        apnCursor.close();
	        return results;
	    }
	}

	private Context context;
}

final class Carriers implements BaseColumns {
    /**
     * The content:// style URL for this table
     */
    public static final Uri CONTENT_URI =
        Uri.parse("content://telephony/carriers");

    /**
     * The default sort order for this table
     */
    public static final String DEFAULT_SORT_ORDER = "name ASC";

    public static final String NAME = "name";

    public static final String APN = "apn";

    public static final String PROXY = "proxy";

    public static final String PORT = "port";

    public static final String MMSPROXY = "mmsproxy";

    public static final String MMSPORT = "mmsport";

    public static final String SERVER = "server";

    public static final String USER = "user";

    public static final String PASSWORD = "password";

    public static final String MMSC = "mmsc";

    public static final String MCC = "mcc";

    public static final String MNC = "mnc";

    public static final String NUMERIC = "numeric";

    public static final String AUTH_TYPE = "authtype";

    public static final String TYPE = "type";

    public static final String CURRENT = "current";
}