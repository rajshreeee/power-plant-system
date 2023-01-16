package com.virtual.powerplant.result;

import lombok.Getter;

import java.util.Map;

@Getter
public class Result<T> {
    private T data;

    private Map<String, Object> metadata;

    private Result(ResultBuilder<T> resultBuilder) {
        this.data = resultBuilder.data;
        this.metadata = resultBuilder.metaData;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public static class ResultBuilder<T> {

        private T data;

        private Map<String, Object> metaData;

        public ResultBuilder(T data) {
            this.data = data;
        }

        public ResultBuilder metaData(Map<String, Object> metaData) {
            this.metaData = metaData;
            return this;
        }

        public Result<T> build() {
            return new Result<>(this);
        }
    }
}
