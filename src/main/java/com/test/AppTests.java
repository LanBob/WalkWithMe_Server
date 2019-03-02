package com.test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.domain.*;
import com.mapper.*;
import com.service.*;
import com.service.impl.HeadImageImpl;
import com.servlet.ViewShow;
import com.util.ContextUtil;
import com.util.JSONUtil;
import com.util.StrUtil;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

//eyAibmFtZSI6InJ1bm9vYiIgLCAidXJsIjoid3d3LnJ1bm9vYi5jb20iIH0=
//
@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:beans.xml")
public class AppTests {

    @Autowired
    public CollectionDao dao = null;
    @Autowired
    public CollectionMapper collectionMapper;
    @Autowired
    private PersonDaoMapper personDaoMapper;
    @Autowired
    private StarCollectionMapper starCollectionMapper;
    @Autowired
    private FindViewMapper findViewMapper;
    @Autowired
    private StarMapper starMapper;
    @Autowired
    private UserFollowMapper userFollowMapper;

//    @2
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ViewShowMapper viewShowMapper;

    @Autowired
    private IViewShowService viewShowService;

    @Autowired
    private IPersonSettingService personSettingService;

    @Autowired
    private IMessageCode messageCode;
    @Autowired
    private IHeadImageService headImageService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IFeedBackService feedBackService;

    @Autowired
    private IIsGoodManService iIsGoodManService;


    @Test
    public void s(){
        String a = StrUtil.toMD5("lj1158");
        System.out.println(a);
    }


    @Test
    public void ss(){
//        IsGoodMan isGoodMan = new IsGoodMan();
//        isGoodMan.setUserName("alian");
//        isGoodMan.setAge("16");
//        isGoodMan.setIntroduce("sfaa");
//        isGoodMan.setSex("男");
//        isGoodMan.setUserId("11578");
//        IsGoodMan isGoodMan = iIsGoodManService.get("1158");
//        System.out.println(isGoodMan);
//        iIsGoodManService.delete("1158");

        List<IsGoodMan> list = iIsGoodManService.listAllAlreadyScore();
        for (IsGoodMan os :
                list) {
            System.out.println(os);
        }
    }
    @Test
    public void t(){
        String money = "1.0";
        if(isBigDecimal(money)){
            BigDecimal bigDecimal = new BigDecimal(money);
            System.out.println(bigDecimal);
        }
    }
    public static boolean isBigDecimal(String str){
        if(str==null || str.trim().length() == 0){
            return false;
        }
        char[] chars = str.toCharArray();
        int sz = chars.length;
        int i = (chars[0] == '-') ? 1 : 0;
        if(i == sz) return false;

        if(chars[i] == '.') return false;//除了负号，第一位不能为'小数点'

        boolean radixPoint = false;
        for(; i < sz; i++){
            if(chars[i] == '.'){
                if(radixPoint) return false;
                radixPoint = true;
            }else if(!(chars[i] >= '0' && chars[i] <= '9')){
                return false;
            }
        }
        return true;
    }


    @Test
    public void viewshwo(){
        List<ViewShowDao> viewShowDao = viewShowMapper.searchByKeyWord("北京");
//        viewShowDao.setId(158L);
//        viewShowMapper.insert(viewShowDao);
        System.out.println(viewShowDao.size());
    }


    @Test
    public void feeed(){
        String userId = "135";
        List<FeedBackDao> list = feedBackService.getFeedBackListByUserId(userId);
        for (FeedBackDao da :
                list) {
            System.out.println(da);
        }
    }
    @Test
    public void feed(){
        FeedBackDao feedBackDao = new FeedBackDao();
        feedBackDao.setUserId("135");
        feedBackDao.setContent("ss");
        feedBackDao.setTitle("55");
        feedBackService.insert(feedBackDao);
    }


    @Test
    public void millToTime(){
        String mill = String.valueOf(System.currentTimeMillis());
        System.out.println(mill);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sd = sdf.format(new Date(Long.parseLong(mill)));
        System.out.println(sd);
    }

    @Test
    public void Comment1(){
        List<CommentDao> list = commentService.getCommentByViewShowId("44");
        for (CommentDao dao :
                list) {
            System.out.println(list);
        }
    }

    @Test
    public void Comment(){
        CommentDao commentDao = new CommentDao();
        commentDao.setComment("11");
        commentDao.setDefaultImage("ss");
        commentDao.setMytime("s");
        commentDao.setUserId("ss");
        commentDao.setViewShowId("44");
        commentDao.setUserName("aaa");
        commentService.insert(commentDao);
    }


    @Test
    public void myFindViewMapper(){
        List<FindViewDao>  list = findViewMapper.get_list_by_userId("13724158682");
        for (FindViewDao da :
                list) {
            System.out.println(da);
        }
        System.out.println(list.size());
    }
    @Test
    public void HeadImage(){
        HeadImage headImage = headImageService.get("13424158682");
        headImage.setId("45");
        headImageService.insert(headImage);
        System.out.println(headImage);
    }

    @Test
    public void getMessageCode(){
        String s = messageCode.sendCodeToUser("13724158682");
        System.out.println(s);
        Map<String, Object> map = JSONUtil.toMap(s);
        System.out.println(map);
//        System.out.println(map.get(""));
    }

    @Test
    public void MessageOCed(){
        System.out.println(StrUtil.getMessageCode());
    }

    @Test
    public void personSetting(){
        PersonSettingDao personSettingDao = new PersonSettingDao();
        personSettingDao.setCity("s");
        personSettingDao.setAge(1);
        personSettingDao.setAlias("s");
        personSettingDao.setIntroduce("s");
        personSettingDao.setLove("s");
        personSettingDao.setPhone_num("1");
        personSettingDao.setSex("s");
        personSettingDao.setId(11L);
        personSettingService.insert(personSettingDao);

    }

    @Test
    public void isInte(){
        String userName = "13756789459";
        System.out.println(isMobile(userName));
    }

    public boolean isMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    public boolean isInteger(String str) {
        //加或减出现一次或者零次，然后数字出现任意次
//        Pattern pattern = Pattern.compile("^[\\d]*$");
        Pattern pattern = Pattern.compile("^[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public List<String> cutStringByImgTag(String targetStr) {
        List<String> splitTextList = new ArrayList<String>();
        Pattern pattern = Pattern.compile("<img.*?src=\\\"(.*?)\\\".*?>");
        Matcher matcher = pattern.matcher(targetStr);
        int lastIndex = 0;
        while (matcher.find()) {
//            if (matcher.start() > lastIndex) {
//                splitTextList.add(targetStr.substring(lastIndex, matcher.start()));
//            }
//            splitTextList.add(targetStr.substring(matcher.start(), matcher.end()));
//            lastIndex = matcher.end();
            System.out.println("sss");
        }

        if (lastIndex != targetStr.length()) {
            splitTextList.add(targetStr.substring(lastIndex, targetStr.length()));
        }
        return splitTextList;
    }

    @Test
    public void Search(){
//        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        ViewShowDao list = viewShowMapper.get(1271182437L);
        System.out.println(list);
//        for (ViewShowDao dao : list) {
//            System.out.println(dao);
//        }
    }

    @Test
    public void Mybatis_Spring() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        List listCollection = collectionMapper.list_collection(5L);
        List personDaoList = personDaoMapper.listAll();
        StarCollectionDao starCollectionDao = starCollectionMapper.get(8L);
        findViewMapper.get_list_by_type(1);
        starMapper.list_star(77L);
        userMapper.get(5L);
        userFollowMapper.get(8L);
        viewShowMapper.get(5L);

        Star star = new Star();
        star.setView_show_id(7L);
        star.setWho_star(5L);
        int s = starMapper.insert(star);
        System.out.println(s);

//		System.out.println(starCollectionDao);


//		System.out.println(dao);
//		System.out.println(dao);
    }


    @Test
    public void Star_collection() {
        StarCollectionMapper mapper = ContextUtil.getBean(StarCollectionMapper.class);
        /*
		Star_collection dao = new Star_collection();
		dao.setId(3);
		dao.setView_show_id(3L);
		dao.setStar(0);
		dao.setCollection(0);
		*/
        mapper.delete(3L);
    }

    @Test
    public void Collection() {
//        CollectionMapper mapper = ContextUtil.getBean(CollectionMapper.class);
        System.out.println( ContextUtil.ctx + " ");
		/*
		Map map = new HashMap<>();
		map.put("view_show_id",1841556183L );
		map.put("who_collection", 116L);
		Collection_dao dao = mapper.get(map);
		*/
//        List<Long> collection_list = mapper.list_collection(116L);
//        for (Long long1 : collection_list) {
//            System.out.println(long1);
//        }
		/*
		Map map = new HashMap<>();
		map.put("view_show_id", 118L);
		map.put("who_collection", 116L);
		mapper.delete(map);
		Collection_dao dao = new Collection_dao();
		dao.setView_show_id(118L);
		dao.setWho_collection(116L);
		mapper.insert(dao);
		session.commit();
		*/
    }

    @Test
    public void star() {
        StarMapper mapper = ContextUtil.getBean(StarMapper.class);
        Map map = new HashMap<>();
        map.put("view_show_id", 1665157564L);
        map.put("who_star", 11L);
        mapper.get(map);
		/*
		Map map = new HashMap<>();
		map.put("who_star", 11L);
		map.put("view_show_id", 1158L);
		mapper.delete(map);
		
		List<Long> list = mapper.list_star(11L);
		for(int i=0;i<list.size();++i) {
			System.out.println(list.get(i));
		}
		
		//Star star = new Star();
		
		star.setView_show_id(1158L);
		star.setWho_star(11L);
		mapper.insert(star);
		session.commit();
		*/

    }

    @Test
    public void user_follow() {
        UserFollowMapper mapper = ContextUtil.getBean(UserFollowMapper.class);
        Map map = new HashMap<>();
        map.put("follower", 13424158682L);
        map.put("followed", 13424158682L);
        mapper.get_user_follow(map);
		/*
		List<Long> list = mapper.get_followed_id(116L);
		for (Long long1 : list) {
			System.out.println(long1);
		}
		
	
		User_follow_dao dao = new User_follow_dao();
		Map map = new HashMap<>();
		map.put("follower", 116L);
		map.put("followed", 115L);
		mapper.delete(map);
		
		dao.setFollowed(115L);
		dao.setFollower(116L);
		mapper.insert(dao);
		session.commit();
		List<User_follow_dao> list = mapper.get(13424158682L);
		*/
    }

    @Test
    public void serrign() {
        Long id = 66666579L;
        String alias = "else_name";
        String sex = "男";
        String love = "游泳";
        String introduce = "世界那么大，我想去看看";
        String city = "广西贵港";
        int age = 11;
        String phone_num = "115";

        PersonSettingDao dao = new PersonSettingDao();
        dao.setAlias(alias);
        dao.setId(id);
        dao.setIntroduce(introduce);
        dao.setLove(love);
        dao.setSex(sex);
        dao.setCity(city);
        dao.setAge(age);
        dao.setPhone_num(phone_num);
        PersonSettingMapper mapper = ContextUtil.getBean(PersonSettingMapper.class);
        mapper.insert(dao);
    }

    @Test
    public void chat_timemessage() throws InterruptedException {

        Long time = System.currentTimeMillis();
        System.out.println("old time " + time / 1000);
        Thread.sleep(1000 * 3);
        time = System.currentTimeMillis();
        System.out.println("new time " + time / 1000);

        Long t = 60L * 60 * 24 * 15;
        System.out.println(t);
    }

    @Test
    public void chat_message() {
        ChatMessageMapper mapper = ContextUtil.getBean(ChatMessageMapper.class);
		/*
		Chat_message_dao dao = new Chat_message_dao();
		dao.setFrom_userID(10L);
		dao.setTo_userID(1001L);
		dao.setMessage("我想");
		dao.setPath(null);
		mapper.insert(dao);
		session.commit();
		
		List<Chat_message_dao> list = mapper.listAll(89L);
		for (Chat_message_dao chat_message_dao : list) {
			System.out.println(chat_message_dao.toString());
		}
		*/
    }


    /**
     * find_view_dao
     */

    @Test
    public void find_view_dao_1() {
        FindViewMapper mapper = ContextUtil.getBean(FindViewMapper.class);
        List<FindViewDao> list = mapper.get_list_by_type(1);
        for (FindViewDao find_viewDao : list) {
            System.out.println(find_viewDao.toString());
        }
		
		/*
		
		mapper.delete(111110L);
		session.commit();
		*/
		/*
		Find_view_dao dao =  new Find_view_dao();
		dao.setCity("广州");
		dao.setId(111110L);
		dao.setMoney(new BigDecimal(10));
		dao.setStar(0);
		dao.setTitle("欢迎你");
		dao.setType(1);
		dao.setUser_id(1158746179L);
		mapper.insert(dao);
		session.commit();
		*/

    }


    /*
     *1540609167618
9167618
     */
    @Test
    public void Person__1() {
        Long time = System.currentTimeMillis();
        System.out.println(time);
        String times = time + "";
        String l = times.substring(3);
        System.out.println(l);
    }

    @Test
    public void Person_1() {
        PersonDaoMapper mapper = ContextUtil.getBean(PersonDaoMapper.class);
        PersonDao dao = mapper.get(1158746179L);
        System.out.println(dao);
    }
}
