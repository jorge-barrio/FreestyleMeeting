package com.example.freestylemeeting.DAO;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MailDAO {
    static FirebaseAuth myAuth;
    static FirebaseFirestore myDatabase;


    public static void getMailEmailAndPassword(mailCallback callback){
        myDatabase = FirebaseFirestore.getInstance();
        DocumentReference docRef = myDatabase.collection("emailEnviador").document("v1KnM1L6BYRJmI9Qfyf2");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Log.d("ANTES2","todavia no he entrado" );
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        String email = (String) document.get("email");
                        String password = (String) document.get("password");
                        callback.onCallback(email,password);
                    }else{
                        Log.d("NOO", "No encontro documento");
                    }
                }

            }
        });

    }
}
