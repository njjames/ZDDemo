package com.nj.zddemo.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.nj.zddemo.R;
import com.nj.zddemo.mvp.presenter.base.MVPPresenter;
import com.nj.zddemo.ui.activity.base.BaseMVPActivity;

import java.util.List;

public class MainActivity extends BaseMVPActivity {

    private BoomMenuButton mBoomMenuButton;

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void initPage(Bundle savedInstanceState) {
        initBmbView();
    }

    @Override
    protected void createPresenters(List<MVPPresenter> presenters) {

    }

    private void initBmbView() {
        mBoomMenuButton = findViewById(R.id.bmb);
        //设置弹出菜单按钮的样式
        mBoomMenuButton.setButtonEnum(ButtonEnum.TextOutsideCircle);
        //设置右下角按钮中的显示样式（点的个数和布局）
        mBoomMenuButton.setPiecePlaceEnum(PiecePlaceEnum.DOT_7_3);
        //设置弹出菜单按钮样式（菜单按钮的个数和布局）
        mBoomMenuButton.setButtonPlaceEnum(ButtonPlaceEnum.SC_7_3);
        //循环设置每个菜单按钮的图片和文字
        mBoomMenuButton.clearBuilders();
        for (int i = 0; i < mBoomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            TextOutsideCircleButton.Builder builder = new TextOutsideCircleButton.Builder()
//                    .rotateText(true)   //设置文字循环滚动，好像不是这个
                    .listener(new OnBMClickListener() { //添加点击事件
                        @Override
                        public void onBoomButtonClick(int index) {
                            Toast.makeText(MainActivity.this, "您点击了" + index, Toast.LENGTH_SHORT).show();
                        }
                    });
            switch (i) {
                case 0:
                    builder.normalImageRes(R.drawable.icon_sales_page).normalTextRes(R.string.sale_name);
                    break;
                case 1:
                    builder.normalImageRes(R.drawable.icon_salessearch).normalTextRes(R.string.sale_search_name);
                    break;
                case 2:
                    builder.normalImageRes(R.drawable.icon_partsmanager).normalTextRes(R.string.part_name);
                    break;
                case 3:
                    builder.normalImageRes(R.drawable.icon_housesearch).normalTextRes(R.string.house_name);
                    break;
                case 4:
                    builder.normalImageRes(R.drawable.icon_pan_page).normalTextRes(R.string.pan_name);
                    break;
                case 5:
                    builder.normalImageRes(R.drawable.icon_clintmanager).normalTextRes(R.string.clint_name);
                    break;
                case 6:
                    builder.normalImageRes(R.drawable.icon_supplymanager).normalTextRes(R.string.supply_name);
                    break;
            }
            mBoomMenuButton.addBuilder(builder);
        }
    }
}
