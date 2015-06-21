package com.hedges.integrationtestlearning;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by rowland-hall on 29/01/15
 */
public interface TestTableDAO extends JpaRepository<TestTable, Integer>
{
}
