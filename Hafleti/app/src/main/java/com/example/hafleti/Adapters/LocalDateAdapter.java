package com.example.hafleti.Adapters;

import android.os.Build;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDate;

public class LocalDateAdapter extends TypeAdapter<LocalDate> {

    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        out.value(value.toString()); // "2025-05-03"
    }

    @Override
    public LocalDate read(JsonReader in) throws IOException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDate.parse(in.nextString());
        }
        return null;
    }
}
