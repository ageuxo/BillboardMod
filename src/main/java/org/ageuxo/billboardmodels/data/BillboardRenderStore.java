package org.ageuxo.billboardmodels.data;

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
    public synchronized boolean addBillboardRender(BillboardRender billboard) {
        return this.billboardRenders.add(billboard);
    }

    @Override
    public synchronized void clearBillboardRenders() {
        this.billboardRenders.clear();
    }

    @Override
    public synchronized void replaceBillboards(List<BillboardRender> replacementBillboards) {
        clearBillboardRenders();
        getBillboardRenders().addAll(replacementBillboards);
    }
}
