package com.example.btl_cookapp;


import androidx.annotation.NonNull;

public enum TypeMonAn {
    MonAnSang(0),
    MonAnTrua(1),
    MonAnToi(2),
    MonAnPhu(3);

    private final int value;

    TypeMonAn(int value) { this.value = value; }

    public int getValue() { return value; }

    public static TypeMonAn getTypeByValue(int value) {
        switch (value) {
            case 0:
                return TypeMonAn.MonAnSang;
            case 1:
                return TypeMonAn.MonAnTrua;
            case 2:
                return TypeMonAn.MonAnToi;
            case 3:
                return TypeMonAn.MonAnPhu;
        }
        return TypeMonAn.MonAnPhu;
    }

    @NonNull
    @Override
    public String toString() {
        switch (getValue()) {
            case 0:
                return "Món ăn sáng";
            case 1:
                return "Món ăn trưa";
            case 2:
                return "Món ăn tối";
            case 3:
                return "Món ăn phụ";
        }
        return "Món ăn phụ";
    }
}
