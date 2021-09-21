package com.dvoraninovich.establishment.model.service;

import com.dvoraninovich.establishment.exception.DaoException;
import com.dvoraninovich.establishment.exception.ServiceException;
import com.dvoraninovich.establishment.model.entity.Order;
import com.dvoraninovich.establishment.model.entity.User;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAll() throws ServiceException;
    Optional<Order> findById(long id) throws ServiceException;
    boolean insert(Order order) throws ServiceException;
    boolean update(Order order) throws ServiceException;

    Optional<Order> getCustomerBasket(long customerId) throws ServiceException;
    Long countDishesAmount(long orderId) throws ServiceException;
    BigDecimal countNewOrderPrice(Order order, BigDecimal newBonusesAmount) throws ServiceException;
    BigDecimal countOrderFinalPrice(long id) throws ServiceException;
    Boolean updateOrderFinalPrice(long id) throws ServiceException;

    Long countOrders() throws ServiceException;
    HashMap<Order, User> findOrdersWithUsersLimit(long minPos, long maxPos) throws ServiceException;

    Long countUserOrders(long userId) throws ServiceException;
    List<Order> findAllUserOrders(long userId) throws ServiceException;
    List<Order> findUserOrders(long userId, long minPos, long maxPos) throws ServiceException;
}
