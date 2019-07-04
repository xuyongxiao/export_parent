package com.itheima.test;

import com.itheima.domain.system.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/applicationContext-*.xml")
public class CompanyTest {

    /**
    @Autowired
    private ICompanyService companyService;

    @Test
    public void testFindAll(){
        List<Company> companyList = companyService.findAll();
        for (Company company : companyList) {
            System.out.println(company);
        }
    }

    @Test
    public void testSave(){
        Company company = new Company();
        company.setName("哈哈哈");
        company.setAddress("广州市");
        company.setExpirationDate(new Date());
        companyService.save(company);
    }
    */
}
