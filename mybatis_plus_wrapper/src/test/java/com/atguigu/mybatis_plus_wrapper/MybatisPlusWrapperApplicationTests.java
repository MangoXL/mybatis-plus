package com.atguigu.mybatis_plus_wrapper;

import com.atguigu.mybatis_plus_wrapper.entity.User;
import com.atguigu.mybatis_plus_wrapper.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.management.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisPlusWrapperApplicationTests {

	@Autowired
	UserMapper userMapper;

	@Test
	public void contextLoads() {
		List<User> users = userMapper.selectList(null);
		users.forEach(System.out::println);
	}

	@Test
	public void testInsert(){
		User user = new User();
		user.setName("admin");
		user.setAge(18);
		user.setEmail("123");
		int i = userMapper.insert(user);
		System.out.println(i > 0?"添加成功":"添加失败");
	}

	@Test//SQL：UPDATE user SET deleted=1 WHERE deleted=0 AND name IS NULL AND age >= ? AND email IS NOT NULL
	public void testDelete(){
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.eq("id", "1")
				.ge("age", 18)
				.isNotNull("email");
		int i = userMapper.delete(wrapper);
		System.out.println(i > 0?"删除成功":"删除失败");
	}

	@Test//SELECT id,name,age,email,create_time,update_time,deleted,version FROM user WHERE deleted=0 AND name = ?
	public void testSelect(){
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.eq("name","admin");
		User user = userMapper.selectOne(wrapper);
		System.out.println(user);
	}

	@Test//SELECT COUNT(1) FROM user WHERE deleted=0 AND age BETWEEN ? AND ?
	public void testBetween(){
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.between("age",18,40);
		Integer count = userMapper.selectCount(wrapper);
		System.out.println("十八岁到四十岁之间有" + count + "人");
	}

	@Test
	//SELECT id,name,age,email,create_time,update_time,deleted,version
	//FROM user WHERE deleted=0 AND name = ? AND id = ? AND age = ?
	public void testAllEq(){
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		HashMap<String, Object> map = new HashMap<>();
		map.put("name","admin");
		map.put("age",18);
		map.put("email","123");
		wrapper.allEq(map);
		List<User> users = userMapper.selectList(wrapper);
		users.forEach(System.out::println);
	}

	@Test
//	SELECT id,name,age,email,create_time,update_time,deleted,version
//	FROM user WHERE deleted=0 AND name NOT LIKE ? AND email LIKE ?
	public void testLike(){
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.notLike("age",2)
				.like("name","a")
				.likeLeft("email","23");
		List<Map<String, Object>> maps = userMapper.selectMaps(wrapper);
		maps.forEach(System.out::println);
	}

	@Test
	public void testNotIn(){
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.notIn("id",1,2,3);
		List<Object> user = userMapper.selectObjs(wrapper);
		user.forEach(System.out::println);
	}

	@Test
	public void testSqlIn(){
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.inSql("age","SELECT age FROM USER GROUP BY age HAVING COUNT(*) > 1");
		List<Object> objects = userMapper.selectObjs(wrapper);
		objects.forEach(System.out::println);
	}

	@Test
//	注意：这里使用的是 UpdateWrapper
//	不调用or则默认为使用 and 连
	public void testOrAnd(){
		User user = new User();
		user.setDeleted(0);
		user.setEmail("666");
		UpdateWrapper<User> wrapper = new UpdateWrapper<>();
		wrapper.like("name","admin")
				.or()
				.between("age",17,19);
		int i = userMapper.update(user, wrapper);
		System.out.println(i > 0?"修改成功":"修改失败");
	}

	@Test
	public void testLambda(){
		User user = new User();
		user.setAge(123);
		UpdateWrapper<User> wrapper = new UpdateWrapper<>();
		wrapper.ne("age",20)
				.or(i -> i.eq("name","admin").like("email","6"));
		int i = userMapper.update(user, wrapper);
		System.out.println(i > 0?"修改成功":"修改失败");
	}

//	@Test
//	public void test(){
//		User user = new User();
//		user.setId(1098913076050247682L);
//		user.setAge(321);
//		int i = userMapper.updateById(user);
//
//	}

	@Test
	public void testOrderBy(){
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.orderByDesc("id");
		List<User> users = userMapper.selectList(wrapper);
		users.forEach(System.out::println);
	}

	@Test
	public void testLast(){
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.last("limit 1");
		List<User> users = userMapper.selectList(wrapper);
		users.forEach(System.out::println);
	}

	@Test
	public void testselect(){
		QueryWrapper<User> wrapper = new QueryWrapper<>();
		wrapper.select("name","age");
		List<User> users = userMapper.selectList(wrapper);
		users.forEach(System.out::println);
	}

	@Test
	public void testSet(){
		User user = userMapper.selectById(1098913076050247682L);
		user.setAge(18);
		UpdateWrapper<User> wrapper = new UpdateWrapper<>();
		wrapper.eq("name","root")
				.set("name","admin")
				.setSql("email = 123456");
		int i = userMapper.update(user, wrapper);
		System.out.println(i > 0?"修改成功":"修改失败");
	}

}
