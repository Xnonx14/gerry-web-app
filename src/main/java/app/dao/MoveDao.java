package app.dao;

import java.io.Serializable;

public class MoveDao implements Serializable {

    private static final long serialVersionUID = -2292329342638710330L;

    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MoveDao && this.getId().equals(((MoveDao) o).getId());
    }
}
