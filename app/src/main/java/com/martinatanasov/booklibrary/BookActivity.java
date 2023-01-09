package com.martinatanasov.booklibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookActivity extends AppCompatActivity {

    public static final String BOOK_ID_KEY = "bookId";

    private TextView txtBookName, txtAuthor, txtDescription, txtPages;
    private Button btnWantToRead, btnAddToAlreadyRead, btnAddToCurrentlyReading, btnAddToFavorite;
    private ImageView bookImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();

//        String longDescription = "Set in the kingdom of Khanduras that is located in the world of Sanctuary," +
//                " Diablo has the player take control of a lone hero or heroine starting outside of their house" +
//                " in town as they set out on a journey to rid the town of Tristram of the titular Lord of Terror," +
//                " and his horde of demon minions lurking in the shadows underground beneath the Cathedral at the very edge of town.";
//
//        //TODO
//        Book book = new Book(1, "Diablo: Legacy of Blood", "Richard Knaak", 300, "https://images-na.ssl-images-amazon.com/images/I/61vAUh-+G+L.jpg",
//                "New work", longDescription);

        Intent intent = getIntent();
        if ( null!= intent) {
            int bookId = intent.getIntExtra(BOOK_ID_KEY, -1);
            if (bookId!=-1){
                Book incomingBook = Utils.getInstance(this).getBookById(bookId);
                if (incomingBook!=null){
                    setData(incomingBook);

                    handleAlreadyRead(incomingBook);
                    handleWantToReadBooks(incomingBook);
                    handleCurrentlyReadingBooks(incomingBook);
                    handleFavoriteBooks(incomingBook);

                }
            }
        }
    }

    private void handleFavoriteBooks(final Book book) {
        ArrayList<Book> favoriteBooks = Utils.getInstance(this).getFavoriteBooks();

        boolean existInFavoriteBooks = false;

        for(Book b: favoriteBooks){
            if (b.getId() == book.getId()){
                existInFavoriteBooks=true;
            }
        }

        if(existInFavoriteBooks){
            btnAddToFavorite.setEnabled(false);
        } else {
            btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToFavorite(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this, FavoriteActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleCurrentlyReadingBooks(final Book book) {
        ArrayList<Book> currentlyReadingBooks = Utils.getInstance(this).getCurrentlyReadingBooks();

        boolean existInCurrentlyReadingBooks = false;

        for(Book b: currentlyReadingBooks){
            if (b.getId() == book.getId()){
                existInCurrentlyReadingBooks=true;
            }
        }

        if(existInCurrentlyReadingBooks){
            btnAddToCurrentlyReading.setEnabled(false);
        } else {
            btnAddToCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToCurrentlyReadingBook(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this, CurrentlyReadingBooksActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void handleWantToReadBooks(final Book book) {
        ArrayList<Book> wantToReadBooks = Utils.getInstance(this).getWantToReadBooks();

        boolean existInWantToReadBooks = false;

        for(Book b: wantToReadBooks){
            if (b.getId() == book.getId()){
                existInWantToReadBooks=true;
            }
        }

        if(existInWantToReadBooks){
            btnWantToRead.setEnabled(false);
        } else {
            btnWantToRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.getInstance(BookActivity.this).addToWantToRead(book)){
                        Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(BookActivity.this, WantToReadActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /* enable and disable btn */
    private void handleAlreadyRead(final Book book){
        ArrayList<Book> alreadyReadBooks = Utils.getInstance(this).getAlreadyReadBooks();

        boolean existInAlreadyReadBooks = false;

        for(Book b: alreadyReadBooks){
            if (b.getId() == book.getId()){
                existInAlreadyReadBooks=true;
            }
        }

        if(existInAlreadyReadBooks){
            btnAddToAlreadyRead.setEnabled(false);
        } else {
            btnAddToAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                if (Utils.getInstance(BookActivity.this).addToAlreadyRead(book)){
                    Toast.makeText(BookActivity.this, "Book Added", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(BookActivity.this, AlreadyReadBookActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(BookActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                }
                }
            });
        }
    }

    private void setData(Book book){
        txtBookName.setText(book.getName());
        txtAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtDescription.setText(book.getLongDesc());
        Glide.with(this)
                .asBitmap().load(book.getImgUrl())
                .into(bookImage);
    }

    private void initViews(){
        txtBookName=findViewById(R.id.txtBookName);
        txtAuthor=findViewById(R.id.txtAuthorName);
        txtDescription=findViewById(R.id.txtDescription);
        txtPages=findViewById(R.id.txtPages);

        btnWantToRead=findViewById(R.id.btnWantToRead);
        btnAddToAlreadyRead=findViewById(R.id.btnAlreadyRead);
        btnAddToCurrentlyReading=findViewById(R.id.btnAddCurrentyReading);
        btnAddToFavorite=findViewById(R.id.btnAddToFavorites);

        bookImage=findViewById(R.id.imgBook);
    }
}