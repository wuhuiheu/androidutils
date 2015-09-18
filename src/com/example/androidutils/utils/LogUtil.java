
package com.example.androidutils.utils;

import android. util .Log ;

public class LogUtil{

      private static final boolean ENABLE_DEBUG = true;
      private static final String TAG = "SystemMessage";

      public static final int FILENAMEINDEX = 0;
      public static final int METHODNAMEINDEX = 1;
      public static final int LINENUMBERINDEX = 2;


      private LogUtil(){}

      public static boolean isDebuggable (){
           return ENABLE_DEBUG;
      }

      private static String createLog (String message, StackTraceElement [] stackTrace ){
           String [] contextInfos = getContextInfos (stackTrace ) ;

           if (contextInfos == null || contextInfos . length < 3 ){
                return "" ;
           }

           String filename = contextInfos [FILENAMEINDEX ] ;
           String methodname = contextInfos [METHODNAMEINDEX ] ;
           String lineNumber = contextInfos [LINENUMBERINDEX ] ;

           StringBuffer sb = new StringBuffer ();
          sb . append( "[ " );
          sb . append( filename );
          sb . append( ", " );
          sb . append( methodname );
          sb . append( ", " );
          sb . append( lineNumber );
          sb . append( "] " );
          sb . append( message );

           return sb. toString ();
      }

      private static String[] getContextInfos (StackTraceElement [] stackTrace ){

           String [] contextInfos = new String[ 3 ];

           String fileName = stackTrace[ 1 ]. getFileName ();
          contextInfos [ FILENAMEINDEX] = fileName. substring( 0 , fileName . indexOf( "." ));
          contextInfos [ METHODNAMEINDEX] = stackTrace [1 ] .getMethodName () ;
          contextInfos [ LINENUMBERINDEX] = Integer .valueOf ( stackTrace[ 1 ]. getLineNumber ()). toString ();
           return contextInfos;
      }

      public static void e (String message){
           if (! isDebuggable ()){
                return;
           }
           StackTraceElement [] stackTrace = new Throwable (). getStackTrace ();
           Log .e ( TAG, createLog( message , stackTrace )) ;
      }

      public static void i (String message){
           if (! isDebuggable ())
                return;

           StackTraceElement [] stackTrace = new Throwable (). getStackTrace ();
           Log .i ( TAG, createLog( message , stackTrace )) ;
      }

      public static void d (String message){
           if (! isDebuggable ())
                return;

           StackTraceElement [] stackTrace = new Throwable (). getStackTrace ();
           Log .d ( TAG, createLog( message , stackTrace )) ;
      }

      public static void v (String message){
           if (! isDebuggable ())
                return;

           StackTraceElement [] stackTrace = new Throwable (). getStackTrace ();
           Log .v ( TAG, createLog( message , stackTrace )) ;
      }

      public static void w (String message){
           if (! isDebuggable ())
                return;

           StackTraceElement [] stackTrace = new Throwable (). getStackTrace ();
           Log .w ( TAG, createLog( message , stackTrace )) ;
      }

      public static void wtf (String message){
           if (! isDebuggable ())
                return;

           StackTraceElement [] stackTrace = new Throwable (). getStackTrace ();
           Log .wtf ( TAG, createLog( message , stackTrace )) ;
      }

      public static void e (String tag, String message ){
           if (! isDebuggable ())
                return;

           StackTraceElement [] stackTrace = new Throwable (). getStackTrace ();
           Log .e ( tag, createLog( message , stackTrace )) ;
      }

      public static void i (String tag, String message ){
           if (! isDebuggable ())
                return;

           StackTraceElement [] stackTrace = new Throwable (). getStackTrace ();
           Log .i ( tag, createLog( message , stackTrace )) ;
      }

      public static void d (String tag, String message ){
           if (! isDebuggable ())
                return;

           StackTraceElement [] stackTrace = new Throwable (). getStackTrace ();
           Log .d ( tag, createLog( message , stackTrace )) ;
      }

      public static void v (String tag, String message ){
           if (! isDebuggable ())
                return;

           StackTraceElement [] stackTrace = new Throwable (). getStackTrace ();
           Log .v ( tag, createLog (message , stackTrace)) ;
      }

      public static void w (String tag, String message ){
           if (! isDebuggable ())
                return;

           StackTraceElement [] stackTrace = new Throwable (). getStackTrace ();
           Log .w ( tag, createLog( message , stackTrace )) ;
      }

      public static void wtf (String tag, String message ){
           if (! isDebuggable ())
                return;

           StackTraceElement [] stackTrace = new Throwable (). getStackTrace ();
           Log .wtf ( tag, createLog( message , stackTrace )) ;
      }

}
