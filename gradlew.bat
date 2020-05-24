<android.support.v4.widget.SwipeRefreshLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:id="@+id/swipe_refresh"
    android:layout_height="match_parent"
    android:background="@color/white">

    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:scrollbarThumbVertical="@android:color/transparent">


        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_margin="@dimen/margin_10">


            <de.hdodenhof.circleimageview.CircleImageView
                android:id="@+id/profile_pic"
                android:layout_width="@dimen/one_twenty"
                android:layout_height="@dimen/one_twenty"
                android:layout_centerHorizontal="true"
                android:layout_gravity="center_horizontal"
                android:layout_marginTop="@dimen/five"
                android:src="@drawable/icon"
                app:civ_border_color="@color/red"
                app:civ_border_overlay="false"
                app:civ_border_width="2dp" />

            <ProgressBar

                android:id="@+id/progressBar"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_centerHorizontal="true"
                android:layout_marginTop="@dimen/fourty_five"
                android:visibility="gone" />

            <RelativeLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_below="@+id/profile_pic"
                android:layout_marginTop="@dimen/margin_10">

                <LinearLayout
                    android:id="@+id/parent"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:orientation="vertical">



                <LinearLayout
                    android:layout_width="match_parent"
                    android:id="@+id