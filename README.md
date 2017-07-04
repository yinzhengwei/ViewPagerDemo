# ViewPagerDemo
viewpager+子viewpager+Fragment,动画翻页以及翻页速度的自定义

在android的v4包开始集成了 fragmentPagerAdapter,这个类顾名思义就是为viewpager+Fragment这种场景而生的，使我们碎片化的管理更加方便，省去了fragment
的维护和一些显示bug的规避。
同时，本应用也利用v4包中的PageTransformer对viewpager的翻页效果进行了处理，，我们可以在PageTransformer中重写transformPage方法，对每个子页进行自定
义属性动画的设置，具体方式是：

public class CubeTransformer implements PageTransformer {

    /**
     * position参数指明给定页面相对于屏幕中心的位置。它是一个动态属性，会随着页面的滚动而改变。当一个页面填充整个屏幕是，它的值是0，
     * 当一个页面刚刚离开屏幕的右边时
     * ，它的值是1。当两个也页面分别滚动到一半时，其中一个页面的位置是-0.5，另一个页面的位置是0.5。基于屏幕上页面的位置
     * ，通过使用诸如setAlpha()、setTranslationX()、或setScaleY()方法来设置页面的属性，来创建自定义的滑动动画。
     */

    @Override
    public void transformPage(View view, float position) {
        if (position <= 0) {
            // 从右向左滑动为当前View
            // 设置旋转中心点；
            ViewHelper.setPivotX(view, view.getMeasuredWidth());
            ViewHelper.setPivotY(view, view.getMeasuredHeight() * 0.5f);
            // 只在Y轴做旋转操作
            ViewHelper.setRotationY(view, 90f * position);
        } else if (position <= 1) {
            // 从左向右滑动为当前View
            ViewHelper.setPivotX(view, 0);
            ViewHelper.setPivotY(view, view.getMeasuredHeight() * 0.5f);
            ViewHelper.setRotationY(view, 90f * position);
        }
    }
}

最后，我们可以给viewpager设置一个Scroller滑动器，这个滑动器里重写了控件startScroll()方法，我们就根据这个方法中的duration参数作为入口，进行修改：
startScroll(startX, startY, dx, dy, mDuration);

主要使用方式是：

        //设置viewpager子页内容
        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentChildA());
        fragmentList.add(new FragmentChildB());
        MyPagerAdapter adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.setList(fragmentList);

        //添加翻页动画
        viewpager.setPageTransformer(false, new CubeTransformer());
        //设置翻页速度，这里为500毫秒
        ViewpagerUtils.controlViewPagerSpeed(getContext(), viewpager, 500);

        viewpager.setAdapter(adapter);
