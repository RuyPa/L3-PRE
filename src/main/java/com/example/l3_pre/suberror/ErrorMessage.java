package com.example.l3_pre.suberror;

import java.io.Serializable;

public interface ErrorMessage extends Serializable {
    int getCode();

    String getMessage();
}
