package app.event;

import app.Sse.ResultsSseData;
import app.dao.MoveDao;

import java.util.List;

public class ResultsEvent extends AbstractApplicationEvent{

    private static final long serialVersionUID = 2831716832308882027L;
    protected int move;

    public ResultsEvent(Object source, ResultsSseData resultData) {
        super(source, resultData.getEventId());
        this.move = resultData.getCount();
    }

    public int getMove() {
        return move;
    }
}
