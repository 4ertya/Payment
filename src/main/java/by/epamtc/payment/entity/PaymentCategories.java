package by.epamtc.payment.entity;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public enum PaymentCategories {
    MOBILE ("mts","a1","life");


    List<String>types;
    PaymentCategories(String ... str){
        types = Arrays.asList(str);
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }
}
