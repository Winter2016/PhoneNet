package com.nc.edu.phonenet.read_write;

import com.nc.edu.phonenet.model.Subscriber;

import java.util.List;

/**
 * Created by Ксения on 2/13/2016.
 */
public interface SubscrReadWriteable  {
    public List<Subscriber> readSubscr();
    public void rewriteSubscr(List<Subscriber> sslist);
    public void writeSubscr(Subscriber ss);
}
