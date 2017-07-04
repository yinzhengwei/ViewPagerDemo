package cn.com.sjyg.viewpagerdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.com.sjyg.viewpagerdemo.Utils.CubeTransformer;
import cn.com.sjyg.viewpagerdemo.Utils.ViewpagerUtils;

/**
 * Created by ethank on 17/7/3.
 */

public class FragmentA extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_a_layout, null);

        ViewPager viewpager2 = (ViewPager) view.findViewById(R.id.viewpager2);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentChildA());
        fragmentList.add(new FragmentChildB());
        MyPagerAdapter adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.setList(fragmentList);

        //添加翻页动画
        viewpager2.setPageTransformer(false, new CubeTransformer());
        //设置翻页速度，这里为500毫秒
        ViewpagerUtils.controlViewPagerSpeed(getContext(), viewpager2, 500);

        viewpager2.setAdapter(adapter);


        return view;
    }
}
