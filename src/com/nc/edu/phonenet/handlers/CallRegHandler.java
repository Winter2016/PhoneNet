package com.nc.edu.phonenet.handlers;

import com.nc.edu.phonenet.model.CallRegister;
import com.nc.edu.phonenet.read_write.CallRegReaderWriter;

import java.util.List;

/**
 * Created by Ксения on 3/16/2016.
 */
public class CallRegHandler {
    private CallRegReaderWriter crrw;

    public CallRegHandler(String filename) {
        crrw = new CallRegReaderWriter(filename);
    }

    public CallRegReaderWriter getCrrw() {
        return crrw;
    }
}
