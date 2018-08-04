package com.nj.zddemo.utils;

import android.graphics.Color;

import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nj.zddemo.R;

/**
 * Created by Administrator on 2018-08-04.
 */

public class BuilderManager {
    private static int[] billImageResources = new int[]{
            R.drawable.icon_housesearch_transparent,
            R.drawable.icon_sales_page_transparent,
            R.drawable.icon_salessearch_transparent,
            R.drawable.icon_pan_page_transparent,
            R.drawable.icon_supplymanager_transparent,
            R.drawable.icon_clintmanager_transparent,
            R.drawable.icon_partsmanager_transparent
    };

    private static int[] billTitleResources = new int[]{
            R.string.house_name,
            R.string.sale_name,
            R.string.sale_search_name,
            R.string.pan_name,
            R.string.supply_name,
            R.string.clint_name,
            R.string.part_name
    };

    private static int[] billColorResources = new int[]{
            Color.parseColor("#1CD4E6"),
            Color.parseColor("#EB644B"),
            Color.parseColor("#71ACE9"),
            Color.parseColor("#D066B1"),
            Color.parseColor("#52C5AC"),
            Color.parseColor("#FFC524"),
            Color.parseColor("#98D56E")
    };

    private static int[] statsImageResources = new int[]{
            R.drawable.icon_report_day,
            R.drawable.icon_report_month,
            R.drawable.icon_report_year,
            R.drawable.icon_report_boss,
            R.drawable.icon_report_xiao,
            R.drawable.icon_report_custom
    };

    private static int[] statsTitleResources = new int[]{
            R.string.report_day,
            R.string.report_month,
            R.string.report_year,
            R.string.report_boss,
            R.string.report_xiao,
            R.string.report_custom
    };

    private static int[] statsColorResources = new int[]{
            Color.parseColor("#71ACE9"),
            Color.parseColor("#FFC524"),
            Color.parseColor("#98D56E"),
            Color.parseColor("#EB644B"),
            Color.parseColor("#D066B1"),
            Color.parseColor("#52C5AC")
    };

    private static int billImageResourceIndex = 0;
    private static int billTitleResourceIndex = 0;
    private static int billColorResourceIndex = 0;

    private static int statsImageResourceIndex = 0;
    private static int statsTitleResourceIndex = 0;
    private static int statsColorResourceIndex = 0;

    public int getBillImageResource() {
        if (billImageResourceIndex >= billImageResources.length)
            billImageResourceIndex = 0;
        return billImageResources[billImageResourceIndex++];
    }

    public int getBillTitleResource() {
        if (billTitleResourceIndex >= billTitleResources.length)
            billTitleResourceIndex = 0;
        return billTitleResources[billTitleResourceIndex++];
    }

    public int getBillColorResource() {
        if (billColorResourceIndex >= billColorResources.length)
            billColorResourceIndex = 0;
        return billColorResources[billColorResourceIndex++];
    }

    public int getStatsImageResource() {
        if (statsImageResourceIndex >= statsImageResources.length)
            statsImageResourceIndex = 0;
        return statsImageResources[statsImageResourceIndex++];
    }

    public int getStatsTitleResource() {
        if (statsTitleResourceIndex >= statsTitleResources.length)
            statsTitleResourceIndex = 0;
        return statsTitleResources[statsTitleResourceIndex++];
    }

    public int getStatsColorResource() {
        if (statsColorResourceIndex >= statsColorResources.length)
            statsColorResourceIndex = 0;
        return statsColorResources[statsColorResourceIndex++];
    }

    public TextInsideCircleButton.Builder getTextInsideCircleButtonBuilder() {
        return new TextInsideCircleButton.Builder()
                .normalImageRes(getBillImageResource())
                .normalColor(getBillColorResource())
                .normalTextRes(getBillTitleResource());
    }

    public TextInsideCircleButton.Builder getSquareTextInsideCircleButtonBuilder() {
        return new TextInsideCircleButton.Builder()
                .normalImageRes(getStatsImageResource())
                .normalColor(getStatsColorResource())
                .normalTextRes(getStatsTitleResource());
    }

    private static BuilderManager ourInstance = new BuilderManager();

    public static BuilderManager getInstance() {
        return ourInstance;
    }

    private BuilderManager() {
    }

}
