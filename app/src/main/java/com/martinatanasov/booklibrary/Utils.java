package com.martinatanasov.booklibrary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

    private static final String ALL_BOOKS_KEY = "all_books";
    private static final String ALREADY_READ_BOOKS = "already_read_books";
    private static final String WANT_TO_READ_BOOKS = "want_to_read_books";
    private static final String CURRENTLY_READING_BOOKS = "currently_reading_books";
    private static final String FAVORITE_BOOKS = "favorite_books";

    private static Utils instance;
    private SharedPreferences sharedPreferences;

//    private static ArrayList<Book> allBooks;
//    private static ArrayList<Book> alreadyReadBooks;
//    private static ArrayList<Book> wantToReadBooks;
//    private static ArrayList<Book> currentlyReadingBooks;
//    private static ArrayList<Book> favoriteBooks;

    private Utils(Context context) {

        sharedPreferences=context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);

        if (null == getAllBooks()){
            initData();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if (null == getAlreadyReadBooks()){
            editor.putString(ALREADY_READ_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if (null == getWantToReadBooks()){
            editor.putString(WANT_TO_READ_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if (null == getCurrentlyReadingBooks()){
            editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }

        if (null == getFavoriteBooks()){
            editor.putString(FAVORITE_BOOKS, gson.toJson(new ArrayList<Book>()));
            editor.commit();
        }
    }

    private void initData() {
        //todo

        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(1, "War of the Spark: Ravnica (Magic: The Gathering)", "Greg Weisman", 400, "https://www.booktopia.com.au/covers/600/9781789092714/1308/ravnica-war-of-the-spark.jpg",
                "New work", "Long description"));
        books.add(new Book(2, "Diablo: Legacy of Blood", "Richard Knaak", 300, "https://images-na.ssl-images-amazon.com/images/I/61vAUh-+G+L.jpg",
                "New work", "Long description"));
        books.add(new Book(3, "Diablo: The Kingdom of Shadow", "Richard Knaak", 200, "https://images-na.ssl-images-amazon.com/images/I/51sYVklhVwL._SX334_BO1,204,203,200_.jpg",
                "New work", "Long description"));
        books.add(new Book(4, "Diablo: The Black Road", "Mel Odom", 219, "https://images-na.ssl-images-amazon.com/images/I/41M6pLpR23L.jpg",
                "New work", "Long description"));
        books.add(new Book(5, "Diablo: Moon of the Spider", "Richard Knaak", 197, "https://images-na.ssl-images-amazon.com/images/I/51JW2fFrf4L._SX308_BO1,204,203,200_.jpg",
                "New work", "Long description"));
        books.add(new Book(6, "WarCraft: Денят на дракона", "Ричард Кнаак", 367, "https://images-na.ssl-images-amazon.com/images/I/81gxm74-csL.jpg",
                "New work", "Long description"));
        books.add(new Book(7, "WarCraft: Повелителят на клановете", "Кристи Голдън", 335, "https://images-na.ssl-images-amazon.com/images/I/617vNvS1EWL.jpg",
                "New work", "Long description"));
        books.add(new Book(8, "WarCraft: Войната на древните - книга първа", "Ричард Кнаак", 413, "https://images-na.ssl-images-amazon.com/images/I/519ytYQPkWL._SX336_BO1,204,203,200_.jpg",
                "New work", "Long description"));
        books.add(new Book(9, "WarCraft: Войната на древните - книга втора", "Ричард Кнаак", 414, "https://images-na.ssl-images-amazon.com/images/I/61EQxxeqPML.jpg",
                "New work", "Long description"));
        books.add(new Book(10, "WarCraft: Войната на древните - книга трета", "Ричард Кнаак", 429, "https://images-na.ssl-images-amazon.com/images/I/5172SPHA3VL._SX307_BO1,204,203,200_.jpg",
                "New work", "Long description"));

        SharedPreferences.Editor editor= sharedPreferences.edit();
        Gson gson = new Gson();
//        String text = gson.toJson(books);
        editor.putString(ALL_BOOKS_KEY, gson.toJson(books));
        editor.commit();
//        editor.apply(); save in background!
    }


    public static Utils getInstance(Context context) {
        if (null!=instance){
            return instance;
        } else {
            instance = new Utils(context);
            return instance;
        }
    }

    public ArrayList<Book> getAllBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALL_BOOKS_KEY, null), type);
        return books;
    }

    public ArrayList<Book> getAlreadyReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(ALREADY_READ_BOOKS, null), type);
        return books;
    }

    public ArrayList<Book> getWantToReadBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(WANT_TO_READ_BOOKS, null), type);
        return books;
    }

    public ArrayList<Book> getCurrentlyReadingBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(CURRENTLY_READING_BOOKS, null), type);
        return books;
    }

    public ArrayList<Book> getFavoriteBooks() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Book>>(){}.getType();
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(FAVORITE_BOOKS, null), type);
        return books;
    }

    public Book getBookById(int id){
        ArrayList<Book> books = getAllBooks();
        if (null!= books){
            for (Book b: books){
                if (b.getId()==id){
                    return b;
                }
            }
        }

        return null;
    }

    public boolean addToAlreadyRead(Book book){
        ArrayList<Book> books = getAlreadyReadBooks();
        if(null != books){
            if(books.add(book)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(ALREADY_READ_BOOKS);
                editor.putString(ALREADY_READ_BOOKS, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToWantToRead (Book book){
        ArrayList<Book> books = getWantToReadBooks();
        if(null != books){
            if(books.add(book)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(WANT_TO_READ_BOOKS);
                editor.putString(WANT_TO_READ_BOOKS, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToCurrentlyReadingBook (Book book){
        ArrayList<Book> books = getCurrentlyReadingBooks();
        if(null != books){
            if(books.add(book)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(CURRENTLY_READING_BOOKS);
                editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean addToFavorite (Book book){
        ArrayList<Book> books = getFavoriteBooks();
        if(null != books){
            if(books.add(book)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(FAVORITE_BOOKS);
                editor.putString(FAVORITE_BOOKS, gson.toJson(books));
                editor.commit();
                return true;
            }
        }
        return false;
    }

    public boolean removeFromAlreadyRead(Book book){
        ArrayList<Book> books= getAlreadyReadBooks();
        if(null != books){
            for(Book b: books){
                if(books.remove(b)){
                    Gson gson = new Gson();
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.remove(ALREADY_READ_BOOKS);
                    editor.putString(ALREADY_READ_BOOKS, gson.toJson(books));
                    editor.commit();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeFromWantToRead(Book book){
        ArrayList<Book> books= getWantToReadBooks();
        if(null != books){
            for(Book b: books){
                if(books.remove(b)){
                    Gson gson = new Gson();
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.remove(WANT_TO_READ_BOOKS);
                    editor.putString(WANT_TO_READ_BOOKS, gson.toJson(books));
                    editor.commit();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeFromCurrentlyReading(Book book){
        ArrayList<Book> books= getCurrentlyReadingBooks();
        if(null != books){
            for(Book b: books){
                if(books.remove(b)){
                    Gson gson = new Gson();
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.remove(CURRENTLY_READING_BOOKS);
                    editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(books));
                    editor.commit();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeFromFavorites(Book book){
        ArrayList<Book> books= getFavoriteBooks();
        if(null != books){
            for(Book b: books){
                if(books.remove(b)){
                    Gson gson = new Gson();
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.remove(FAVORITE_BOOKS);
                    editor.putString(FAVORITE_BOOKS, gson.toJson(books));
                    editor.commit();
                    return true;
                }
            }
        }
        return false;
    }
}
