package com.example.mynfcdemon;

import java.io.IOException;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;

public class TagView extends Activity{
	
	// NFC parts
	private static NfcAdapter mAdapter;
	private static PendingIntent mPendingIntent;
	private static IntentFilter[] mFilters;
	private static String[][] mTechLists;
	
	private TextView texttagid;
	private TextView texttagdata;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			setContentView(R.layout.tag_vierwr);
			texttagdata = (TextView) findViewById(R.id.tagdatatext);
			texttagid = (TextView) findViewById(R.id.tagidtext);
			
			mAdapter = NfcAdapter.getDefaultAdapter(this);
			// Create a generic PendingIntent that will be deliver to this activity.
			// The NFC stack
			// will fill in the intent with the details of the discovered tag before
			// delivering to
			// this activity.
			mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
					getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
			// Setup an intent filter for all MIME based dispatches
			IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);

			try {
				ndef.addDataType("*/*");
			} catch (MalformedMimeTypeException e) {
				throw new RuntimeException("fail", e);
			}
			mFilters = new IntentFilter[] { ndef, };

			// Setup a tech list for all NfcV tags
			mTechLists = new String[][] { new String[] { NfcV.class.getName() } };

			try {
				rfid_scanresult(getIntent());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		void rfid_scanresult(Intent intent) throws IOException{
			
			String action = intent.getAction();
			if(NfcAdapter.ACTION_NDEF_DISCOVERED == action
				|| NfcAdapter.ACTION_TECH_DISCOVERED == action
				|| NfcAdapter.ACTION_TAG_DISCOVERED == action){
			//if(NfcAdapter.ACTION_TECH_DISCOVERED == action){
				//byte[] tagid = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
				
				Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
				byte[] tagid = tag.getId();
				//String strid = new String(tagid);
				//System.out.println("TAGID:"+strid);
				//System.out.println("TAGID:"+bytesToHexString(tagid));
				//texttagid.setText("TagID=" + bytesToHexString(tagid));
				
				Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
				NfcV nfcv = NfcV.get(tagFromIntent);
				nfcv.connect();
				NfcVUtil mNfcVutil = new NfcVUtil(nfcv);

				texttagid.setText("UID:" + mNfcVutil.getUID()+'\n'
						+"AFI:" + mNfcVutil.getAFI()+'\n'
						+"DSFID:" + mNfcVutil.getDSFID()+'\n'
						+"BlockNumber:" + mNfcVutil.getBlockNumber()+'\n'
						+"BlockSize:" + mNfcVutil.getOneBlockSize());
				//NfcVClassCard mifareClassCard=null;
				texttagdata.setText("block0:"+mNfcVutil.readOneBlock(0)
						+"block1:"+mNfcVutil.readOneBlock(1)+'\n'
						+"block2:"+mNfcVutil.readOneBlock(2)
						+"block3:"+mNfcVutil.readOneBlock(3)+'\n'
						+"block4:"+mNfcVutil.readOneBlock(4)
						+"block5:"+mNfcVutil.readOneBlock(5)+'\n'
						+"block6:"+mNfcVutil.readOneBlock(6)
						+"block7:"+mNfcVutil.readOneBlock(7)+'\n'
						+"block8:"+mNfcVutil.readOneBlock(8)
						+"block9:"+mNfcVutil.readOneBlock(9)+'\n'
						+"block10:"+mNfcVutil.readOneBlock(10)
						+"block11:"+mNfcVutil.readOneBlock(11)+'\n'
						+"block12:"+mNfcVutil.readOneBlock(12)
						+"block13:"+mNfcVutil.readOneBlock(13)+'\n'
						+"block14:"+mNfcVutil.readOneBlock(14)
						+"block15:"+mNfcVutil.readOneBlock(15)+'\n'
						+"block16:"+mNfcVutil.readOneBlock(16)
						+"block17:"+mNfcVutil.readOneBlock(17)+'\n'
						+"block18:"+mNfcVutil.readOneBlock(18)
						+"block19:"+mNfcVutil.readOneBlock(19)+'\n'
						+"block20:"+mNfcVutil.readOneBlock(20)
						+"block21:"+mNfcVutil.readOneBlock(21)+'\n'
						+"block22:"+mNfcVutil.readOneBlock(22)
						+"block23:"+mNfcVutil.readOneBlock(23)+'\n'
						+"block24:"+mNfcVutil.readOneBlock(24)
						+"block25:"+mNfcVutil.readOneBlock(25)+'\n'
						+"block26:"+mNfcVutil.readOneBlock(26)
						+"block27:"+mNfcVutil.readOneBlock(27)+'\n'
						+"Read:"+ mNfcVutil.readBlocks(0, 28)
						);
				/*String str;
				str = getNdefMessages(intent).toString();
				
				System.out.println("Ndef::"+str);*/
				String s = "kaic";
				mNfcVutil.writeBlock(0, s.getBytes());
			
			}
		
			
		}
		
		NdefMessage[] getNdefMessages(Intent intent) {
		    // Parse the intent
		    NdefMessage[] msgs = null;
		    String action = intent.getAction();
		    if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
		        Parcelable[] rawMsgs =intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		        if (rawMsgs != null) {
		            msgs = new NdefMessage[rawMsgs.length];
		            for (int i = 0; i < rawMsgs.length; i++) {
		                msgs[i] = (NdefMessage) rawMsgs[i];
		            }
		        }
		        else {
		        // Unknown tag type
		            byte[] empty = new byte[] {};
		            NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty,empty);
		            NdefMessage msg = new NdefMessage(new NdefRecord[] {record});
		            msgs = new NdefMessage[] {msg};
		        }
		    }        
		    else {
		       // Log.e(TAG, "Unknown intent " + intent);
		        finish();
		    }
		    return msgs;
		}
		
		void write_NdefFormatableTag(){
			/*NdefFormatable tag = NdefFormatable.get(t);
			Locale locale = Locale.US;
			final byte[] langBytes = locale.getLanguage().getBytes(Charsets.US_ASCII);
			String text = "Tag, you're it!";
			final byte[] textBytes = text.getBytes(Charsets.UTF_8);
			final int utfBit = 0;
			final char status = (char) (utfBit + langBytes.length);
			final byte[] data = Bytes.concat(new byte[] {(byte) status}, langBytes, textBytes);
			NdefRecord record = NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, newbyte[0], data);
			try {
			    NdefRecord[] records = {text};
			    NdefMessage message = new NdefMessage(records);
			    tag.connect();
			    tag.format(message);
			}
			catch (Exception e){
			    //do error handling
			}*/

		}

		
		
		public static String bytesToHexString(byte[] src){       
			            StringBuilder stringBuilder = new StringBuilder();       
			            if (src == null || src.length <= 0) {       
			                return null;       
			            }       
			            for (int i = 0; i < src.length; i++) {       
			                int v = src[i] & 0xFF;       
			                String hv = Integer.toHexString(v);       
			                if (hv.length() < 2) {       
			                    stringBuilder.append(0);       
			                }       
			                stringBuilder.append(hv);       
			            }       
			            return stringBuilder.toString();       
			        }

}
