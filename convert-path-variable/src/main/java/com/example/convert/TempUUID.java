package com.example.convert;

public record TempUUID(String uuid) {
    @Override
    public String toString() {
        return this.uuid;
    }
}
