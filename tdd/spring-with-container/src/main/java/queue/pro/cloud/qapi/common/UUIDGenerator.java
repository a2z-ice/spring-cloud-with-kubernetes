package queue.pro.cloud.qapi.common;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

@Slf4j
public class UUIDGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object arg) throws HibernateException {

        log.info("UUID generation");

        try {

            final Method m = arg.getClass().getMethod("getId");

            if (!m.getReturnType().equals(String.class)) {
                throw new NoSuchMethodException();
            }

            final String invoke = (String)m.invoke(arg);

            return invoke == null ? UUID.randomUUID().toString() : invoke;

        } catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException |
                 InvocationTargetException ex) {
            throw new HibernateException("invalid entity");
        }

    }

}