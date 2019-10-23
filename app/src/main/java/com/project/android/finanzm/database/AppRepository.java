package com.project.android.finanzm.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.project.android.finanzm.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class AppRepository {
    private static AppRepository ourInstance;

    private LiveData<List<User>> mUsers;
    protected static User loggedUser;
    private AppDatabase mDb;
    protected Product salesProduct;
    List<Product> products;

    private LiveData<List<Product>> mProducts;
    private LiveData<List<Product>> mProductsByCategories;

    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {


        if(ourInstance == null){
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mUsers = getAllUsers();
        mProducts = getAllProducts();
        //addSamplesCategories();
        if(mProducts == null)
            Log.i("INITPRODUCT", "failled");
        else
            products = mProducts.getValue();

    }

    public void initProductList(){

        Log.i("Repository", "Got called for products");
        mProducts = getAllProducts();
    }

    public void addSampleProducts(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.productDao().insertAllArticles(getSampleProducts());

                Log.i("init Database Product", String.valueOf(mDb.usersDao().getCount()));
            }
        });
    }

    public void addSamplesCategories(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.categorieDao().insertAllCategorie(getSampleCategories());
            }
        });
    }

    List<Product> getSampleProducts(){
        List<Product> articles = new ArrayList<>();
        articles.add(new Product(40000, "Iphone6", (float) 515, R.drawable.ic_launcher_background, 1  ));
        articles.add(new Product(40001, "Samsung S6", (float) 275.99, R.drawable.ic_launcher_background, 1));
        articles.add(new Product(40002, "Huawei", (float) 295, R.drawable.ic_launcher_background, 1));

        articles.add(new Product(30000, "Iphone X", (float) 1050, R.drawable.ic_launcher_background, 2  ));
        articles.add(new Product(30001, "Samsung S10", (float) 699, R.drawable.ic_launcher_background, 2));
        articles.add(new Product(30002, "Huawei P7", (float) 950, R.drawable.ic_launcher_background, 2));

        articles.add(new Product(20001, "Iphone 8", (float) 1050, R.drawable.ic_launcher_background, 3  ));
        articles.add(new Product(20002, "Samsung S8", (float) 699, R.drawable.ic_launcher_background, 3));
        articles.add(new Product(20003, "Huawei P5", (float) 950, R.drawable.ic_launcher_background, 3));


        articles.add(new Product(40000, "Iphone 5S", (float) 1050, R.drawable.ic_launcher_background, 4  ));
        articles.add(new Product(40001, "Samsung S5", (float) 699, R.drawable.ic_launcher_background, 4));
        articles.add(new Product(40002, "Huawei P1", (float) 950, R.drawable.ic_launcher_background, 4));
        return articles;
    }

    public void addSampleUser(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.usersDao().insertAllUsers(getAllUser());

                Log.i("init Database", String.valueOf(mDb.usersDao().getCount()));
            }
        });
    }

    public ArrayList<User> getAllUser(){
        Log.i("Database", String.valueOf(mDb.usersDao().getCount()));
        ArrayList<User> users = new ArrayList<>();
        users.add(new User( "firstname", "name", 280794, 1994));
        users.add(new User( "firstname", "name", 240895, 1995));
        users.add(new User( "firstnag", "name", 240445, 1995));
        users.add(new User( "fi1rstnag", "name", 240445, 1995));
        users.add(new User( "firsgname", "name", 240795, 1995));
        return users;
    }
// User
    // Get the list of all avalaible User
    public LiveData<List<User>> getAllUsers(){
                return mDb.usersDao().getAllUsers();
    }



    public User getUsersById(final int userId) {


        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.i("Database", "Count"+String.valueOf(mDb.usersDao().getCount()));
                AppRepository.setUser(mDb.usersDao().getUserByUserId(userId)) ;

            }
        });


        return getLoggedUser();
    }

    private User getLoggedUser() {
        return loggedUser;
    }



    private static void setUser(User userByUserId) {
        loggedUser = userByUserId;
        Log.i("Repository", "Got called");
    }


    public long generateReceipt(final SalesReceipt receipt){
        try {
            Callable<Long> callable = new Callable<Long>() {
                @Override
                public Long call() throws Exception {
                    return mDb.receiptDao().insertSalesReceipt(receipt);
                }
            };

            Future<Long> future = Executors.newSingleThreadExecutor().submit(callable);


            return future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

// Sales
    // Insert sales to the table
    public void addSales(final List<Sales> salesItems) {
        executor.execute(new Runnable() {
                             @Override
                             public void run() {

                                 mDb.salesDao().insertAllSalesItem(salesItems);
                             }
                         }
        );
    }
// Suspend&retrieved


// Products

    // Return the list of products
    public LiveData<List<Product>> getAllProducts(){

        return mDb.productDao().getAllProducts();
    }

    /*
    *
    *
    */
    public Product getProductByProductCode(final int code){

        return mDb.productDao().getArticleByCode(code);
    }



    public void addSuspendedReceipt(final List<Sales> sales) {
        executor.execute(new Runnable() {
                             @Override
                             public void run() {

                                 mDb.suspendAndRetrievedDao().insertAllSalesItem(sales);
                             }
                         }
        );
    }

    public LiveData<List<ProductCategories>> getAllCategories() {

        return mDb.categorieDao().getAll();
    }


    public List<ProductCategories> getSampleCategories() {
        List<ProductCategories> categories = new ArrayList<>();

        categories.add(new ProductCategories("Telefon", R.drawable.ic_launcher_background));
        categories.add(new ProductCategories("High Class", R.drawable.ic_launcher_background));
        categories.add(new ProductCategories("Food", R.drawable.ic_launcher_background));
        categories.add(new ProductCategories("Accessoires", R.drawable.ic_launcher_background));
        return categories;
    }


    /*
    *
    *
    */
    public LiveData<List<Product>> getProductByCategories(final int id) {
        return mDb.productDao().getArticleByCategories(id);
    }

    public int getCategoriesCount() {
        try {
            Callable<Integer> callable = new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    return mDb.categorieDao().getCount();
                }
            };

            Future<Integer> future = Executors.newSingleThreadExecutor().submit(callable);


            return future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;

    }

    public LiveData<List<SalesReceipt>> getAllSalesReceipt() {
        return mDb.receiptDao().getAll();
    }

    public void insertNewArticles(final Product product) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Product code = null;
                code = mDb.productDao().getArticleByCode(product.getArticleCode());
                if(code == null){
                    mDb.productDao().insertArticle(product);
                }else {
                    mDb.productDao().insertArticle(product);
                }
            }
        });
    }

    public void insertNewUsers(final User p) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.usersDao().insertUser(p);
            }
        });
    }

    public AppConfig getAppConfigByDescription(final String isFirstStart) {
        try {
            Callable<AppConfig> callable = new Callable<AppConfig>() {
                @Override
                public AppConfig call() throws Exception {
                    AppConfig app = mDb.appConfigDao().getAppConfigByDescription("isFirstStart");
                    AppConfig app1;
                    Log.i("AppConfig", "descr : " + isFirstStart);
                    if(app == null){
                        app1 = new AppConfig("isFirstStart", 0);
                        mDb.appConfigDao().insertCategorie(app1);
                        Log.i("AppConfig", "count : " + mDb.appConfigDao().getCount());
                    }


                    return app;
                }
            };

            Future<AppConfig> future = Executors.newSingleThreadExecutor().submit(callable);


            return future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateAppConfig(final String is_first_start, final int value) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                AppConfig appConfig = mDb.appConfigDao().getAppConfigByDescription(is_first_start);
                if(appConfig != null){
                    appConfig.setValue(value);
                    mDb.appConfigDao().insertCategorie(appConfig);
                }
            }
        });
    }
}
