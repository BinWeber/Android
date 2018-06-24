package top.binweber.lovertrees;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import com.bigkoo.convenientbanner.holder.Holder;

/**
 * 图片轮播适配器
 */
public class LocalImageHolderView implements Holder<Integer>{

    private ImageView imageView;
    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, Integer data) {
        imageView.setImageResource(data);
    }
}
