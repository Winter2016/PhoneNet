package com.nc.edu.phonenet.read_write;

import com.nc.edu.phonenet.model.CallRegister;
import com.nc.edu.phonenet.model.Subscriber;

import java.util.List;

/**
 * Created by Ксения on 3/8/2016.
 */
public interface CallRegReadWriteable {
    public List<CallRegister> readCallReg();
    public void rewriteCallReg(List<CallRegister> crlist);
    public void writeCallReg(CallRegister cr);
}
