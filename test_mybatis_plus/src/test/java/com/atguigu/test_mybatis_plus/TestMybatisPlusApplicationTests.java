package com.atguigu.test_mybatis_plus;

import com.atguigu.test_mybatis_plus.entity.User;
import com.atguigu.test_mybatis_plus.mapper.UserMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMybatisPlusApplicationTests {

	@Autowired
	UserMapper userMapper;

	@Test
	public void contextLoads() {
		System.out.println("Select All");
		List<User> list = userMapper.selectList(null);
		list.forEach(System.out::println);
	}

	@Test
	public void testInsert(){
		User user = new User();
		user.setId(666L);
		user.setName("admin");
		user.setAge(18);
		user.setEmail("123@qq.com");
		int i = userMapper.insert(user);
		System.out.println(i > 0?"添加成功":"添加失败");
	}

	@Test
	public void testUpdateById(){
		User user = new User();
		user.setName("zhangsan");
		user.setId(1098805837566148610L);
		int i = userMapper.updateById(user);
		System.out.println(i > 0?"修改成功":"修改失败");
	}

	@Test
	public void testCreateTime(){
		User user = new User();
		user.setName("lisi");
		user.setAge(20);
		user.setEmail("123");
		int i = userMapper.insert(user);
		System.out.println(i > 0?"添加成功":"添加失败");
	}

	@Test
	public void testUpdateTime(){
		User user = new User();
		user.setId(1098868150889377793L);
		user.setName("lisi");
		user.setAge(22);
		user.setEmail("321");
		int i = userMapper.updateById(user);
		System.out.println(i > 0?"修改成功":"修改失败");
	}

	@Test
	public void testVersion(){
		User user = userMapper.selectById(666L);
		System.out.println(user);
		user.setAge(111);
		int i = userMapper.updateById(user);
		System.out.println(i > 0?"修改成功":"修改失败");
	}

	@Test
	public void testVersion01(){
		User u1 = userMapper.selectById(666L);
		u1.setAge(222);
		User u2 = userMapper.selectById(666L);
		u2.setAge(333);
		int i1 = userMapper.updateById(u2);
		int i2 = userMapper.updateById(u1);
		System.out.println(i2 > 0?"修改成功":"修改失败");
		System.out.println(i1 > 0?"修改成功":"修改失败");
	}

	@Test
	public void testSelectBatch(){
		List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3, 4, 5));
		users.forEach(System.out::println);
	}

	@Test
	public void testSelectMap(){
		HashMap<String, Object> map = new HashMap<>();
		map.put("name","zhangsan");
		map.put("age",18);
		map.put("email","123@qq.com");
		List<User> users = userMapper.selectByMap(map);
		users.forEach(System.out::println);
	}

	@Test
	public void testSelectPage(){
		Page<User> page = new Page<>(0,5);
		userMapper.selectPage(page, null);
		List<User> records = page.getRecords();
		records.forEach(System.out::println);
		System.out.println(page.getSize());
		System.out.println(page.getTotal());
		System.out.println(page.getCurrent());
		System.out.println(page.getPages());
		System.out.println(page.hasNext());
		System.out.println(page.hasPrevious());
	}

	@Test
	public void testSelectByMap(){
		Page<User> page = new Page<>(1, 5);
		IPage<Map<String, Object>> iPage = userMapper.selectMapsPage(page, null);
		List<Map<String, Object>> records = iPage.getRecords();
		records.forEach(System.out::println);
		System.out.println(page.getSize());
		System.out.println(page.getTotal());
		System.out.println(page.getCurrent());
		System.out.println(page.getPages());
		System.out.println(page.hasNext());
		System.out.println(page.hasPrevious());
	}

	@Test
	public void testDeleteBatch(){
		int i = userMapper.deleteBatchIds(Arrays.asList(1098805837566148610L, 1098805837566148611L, 1098868150889377793L));
		System.out.println(i > 0?"删除成功":"删除失败");
	}

	@Test
	public void testDeleteByMap(){
		HashMap<String, Object> map = new HashMap<>();
		map.put("name","admin");
		map.put("age",333);
		int i = userMapper.deleteByMap(map);
		System.out.println(i > 0?"删除成功":"删除失败");
	}

	@Test
	public void testDeleteLogic(){
		int i = userMapper.deleteById(1L);
		System.out.println(i > 0?"删除成功":"删除失败");
	}

	@Test
	public void testGet(){
		List<User> users = userMapper.selectList(null);
		users.forEach(System.out::println);
	}

	@Test
	public void testSelect(){
		User user = userMapper.selectById(2);
		System.out.println(user);
	}
}
