package org.ageuxo.billboardmodels.capability;

import java.util.ArrayList;
import java.util.List;

public class BillboardRenderStore implements IBillboardRenderStore {

    private final List<BillboardRender> billboardRenders = new ArrayList<>();

    public BillboardRenderStore() {
    }

    @Override
    public synchronized List<BillboardRender> getBillboardRenders() {
        return billboardRenders;
    }

    @Override
    public synchronized void addBillboardRender(BillboardRender billboard) {
        this.billboardRenders.add(billboard);
    }

    @Override
    public synchronized void clear() {
        this.billboardRenders.clear();
    }
}
