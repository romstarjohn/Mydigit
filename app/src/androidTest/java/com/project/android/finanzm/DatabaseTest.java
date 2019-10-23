package com.project.android.finanzm;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.project.android.finanzm.database.AppDatabase;
import com.project.android.finanzm.database.CategorieDao;
import com.project.android.finanzm.database.Griditem;
import com.project.android.finanzm.database.Product;
import com.project.android.finanzm.database.ProductDao;
import com.project.android.finanzm.database.ReceiptDao;
import com.project.android.finanzm.database.Sales;
import com.project.android.finanzm.database.SalesDao;
import com.project.android.finanzm.database.SalesReceipt;
import com.project.android.finanzm.database.Taxe;
import com.project.android.finanzm.database.TaxeDao;
import com.project.android.finanzm.database.Type;
import com.project.android.finanzm.database.User;
import com.project.android.finanzm.database.UsersDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    public static final String TAG = "JUnit";
    private AppDatabase mDb;
    private CategorieDao categorieDao;
    private ProductDao productDao;
    private UsersDao usersDao;
    private TaxeDao taxeDao;
    private SalesDao salesDao;
    private ReceiptDao receiptDao;


    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();

        categorieDao = mDb.categorieDao();
        productDao = mDb.productDao();
        usersDao = mDb.usersDao();
        taxeDao = mDb.taxesDao();
        receiptDao = mDb.receiptDao();
        salesDao = mDb.salesDao();

        Log.i(TAG, "createDb");

    }

    @After
    public void closeDb(){
        mDb.close();
        Log.i(TAG, "CloseDb");
    }

    @Test
    public void createAndRetrieveCat(){

        ArrayList<Griditem> gridItems = new ArrayList<>();
        gridItems.add(new Griditem("Food", R.drawable.ic_launcher_background, Type.CATEGORIES));
        gridItems.add(new Griditem("Foodtruck", R.drawable.ic_launcher_background, Type.CATEGORIES));
        gridItems.add(new Griditem("Foodtruck", R.drawable.ic_launcher_background, Type.CATEGORIES));
        gridItems.add(new Griditem("Foodtruck", R.drawable.ic_launcher_background, Type.CATEGORIES));

        categorieDao.insertAllCategorie(gridItems);
        int count = categorieDao.getCount();
        Log.i(TAG, "Createandretrieve count " + count);
        assertEquals(4, count);
    }

    @Test
    public void compareString(){

        ArrayList<Griditem> gridItems = new ArrayList<>();
        gridItems.add(new Griditem("Food", R.drawable.ic_launcher_background, Type.CATEGORIES));
        gridItems.add(new Griditem("Foodtruck", R.drawable.ic_launcher_background, Type.CATEGORIES));
        gridItems.add(new Griditem("Foodtruck", R.drawable.ic_launcher_background, Type.CATEGORIES));
        gridItems.add(new Griditem("Foodtruck", R.drawable.ic_launcher_background, Type.CATEGORIES));

        categorieDao.insertAllCategorie(gridItems);categorieDao.getCategorieById(0);

        assertEquals("Food",categorieDao.getCategorieById(1).getDesc() );

        categorieDao.insertCategorie(new Griditem("Career", R.drawable.logo,Type.PRODUCT));
        assertEquals(Type.PRODUCT,categorieDao.getCategorieById(5).getType());

    }

    @Test
    public void compareProductString(){

        ArrayList<Product> articles = new ArrayList<>();
        articles.add(new Product(1110, "R.drawable.ic_launcher_background", (float) 1.5));
        articles.add(new Product(1150, "R.drawable.ic_launcher_background1", (float) 2.75));
        articles.add(new Product(2400, "ic_launcher_background1", (float) 2.95));
        productDao.insertAllArticles(articles);


        assertEquals("ic_launcher_background1",productDao.getArticleById(3).getDescription() );

        productDao.insertArticle(new Product(2800, "ic_launcher_background1", (float) 2.95));
        assertEquals(2800,productDao.getArticleById(4).getArticleCode());

    }

    @Test
    public void VerifyUserTable(){

        ArrayList<User> users = new ArrayList<>();
        users.add(new User( "firstname", "name", 280794, 1994));
        users.add(new User( "firstname", "name", 240895, 1995));
        users.add(new User( "firstnag", "name", 240445, 1995));
        users.add(new User( "fi1rstnag", "name", 240445, 1995));
        users.add(new User( "firsgname", "name", 240795, 1995));

        mDb.usersDao().insertAllUsers(users);


        assertEquals(4, mDb.usersDao().getCount());
    }

    public void VerifyTaxe(){
        List<Taxe> taxes = new ArrayList<>();
        taxes.add(new Taxe("A", (float) 19.25));
        mDb.taxesDao().insertAllTaxeItem(taxes);

        assertEquals(1, mDb.taxesDao().getCount());
    }

    public void VerifySalesTable(){
        List<Sales> taxes = new ArrayList<>();
        taxes.add(new Sales());
        mDb.salesDao().insertAllSalesItem(taxes);

        assertEquals(1, mDb.salesDao().getCount());
    }

    public void VerifySalesReceipt(){
        List<SalesReceipt> taxes = new ArrayList<>();
        taxes.add(new SalesReceipt());
        mDb.receiptDao().insertAllSalesReceipt(taxes);

        assertEquals(1, mDb.salesDao().getCount());
    }
}
