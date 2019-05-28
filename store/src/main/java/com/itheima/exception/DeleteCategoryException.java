package com.itheima.exception;

import java.sql.SQLException;

/**
 * @Project: com.itheima.exception
 * @Author: ShaoDi Wang
 * @Date: Created in 2019-05-28 23:05
 * @Description:
 * @Version: 1.0
 */
public class DeleteCategoryException extends Exception {
    /**
     * Constructs a <code>SQLException</code> object with a given
     * <code>reason</code>. The  <code>SQLState</code>  is initialized to
     * <code>null</code> and the vendor code is initialized to 0.
     * <p>
     * The <code>cause</code> is not initialized, and may subsequently be
     * initialized by a call to the
     * {@link Throwable#initCause(Throwable)} method.
     * <p>
     *
     * @param reason a description of the exception
     */
    public DeleteCategoryException(String reason) {
        super(reason);
    }
}
