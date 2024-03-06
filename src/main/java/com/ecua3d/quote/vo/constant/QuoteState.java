package com.ecua3d.quote.vo.constant;

public enum QuoteState {
    PENDING (1),
    DONE (2);
    public final Integer value;

    QuoteState(int valor) {
        this.value = valor;
    }
}
