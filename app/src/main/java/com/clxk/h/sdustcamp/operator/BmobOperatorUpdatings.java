package com.clxk.h.sdustcamp.operator;

public class BmobOperatorUpdatings {
   /* private static Updatings node;
    private static Context context;

    private UpdatingsAdapter myAdapter;
    public List<Updatings> source = new ArrayList<Updatings>();

    public BmobOperatorUpdatings() {
    }
    public void add(Context context, Updatings node) {
        this.context = context;
        this.node = node;
        node.save(new SaveListener<String>() {

            @Override
            public void done(String arg0, BmobException arg1) {
                // TODO Auto-generated method stub
                if(arg1 == null) {
                    Log.i("数据插入成功", "1111");;
                } else {
                    Log.i("数据插入失败", "1111");
                }
            }
        });
    }

    public void delete(Context context, Updatings node) {
        this.context = context;
        this.node = node;
        node.delete(new UpdateListener() {

            @Override
            public void done(BmobException arg0) {
                // TODO Auto-generated method stub
                if(arg0 == null) {
                    Log.i("数据删除成功", "1111");;
                } else {
                    Log.i("数据删除失败", "1111");
                }
            }
        });
    }

    public void update(Context context, Updatings node) {
        this.context = context;
        this.node = node;
        node.update(new UpdateListener() {

            @Override
            public void done(BmobException arg0) {
                // TODO Auto-generated method stub
                if(arg0 == null) {
                    Log.i("数据更新成功", "1111");;
                } else {
                    Log.i("数据更新失败", "1111");
                }
            }
        });
    }

    public void queryAll(final Context context) {
        this.context = context;
        BmobQuery<Updatings> bmobQuery = new BmobQuery<Updatings>();
        bmobQuery.findObjects(new FindListener<Updatings>() {

            @Override
            public void done(List<Updatings> object, BmobException arg1) {
                // TODO Auto-generated method stub
                if(arg1 == null) {
                    Log.i("查询成功：共" + object.size() + "条数据。","123");
                    for(Updatings t: object) {
                        source.add(t);
                        Log.i("111","1234");
                    }
                    Log.i("qyqyqyq",source.size() + "");
                } else {
                    Log.i("查询失败","222");
                }
            }
        });
    }*/

}
