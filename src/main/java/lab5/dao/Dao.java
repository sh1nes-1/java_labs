package lab5.dao;

import lab5.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> findById(int id) throws DaoException;

    List<T> findAll() throws DaoException;

    boolean insert(T t) throws DaoException;

    boolean update(T t) throws DaoException;

    boolean delete(T t) throws DaoException;

}
