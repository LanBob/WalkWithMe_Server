package com.service.impl;

import com.domain.FindViewDao;
import com.domain.InterScore;
import com.domain.StarCollectionDao;
import com.domain.ViewShowDao;
import com.mapper.FindViewMapper;
import com.mapper.InterScoreMapper;
import com.mapper.StarCollectionMapper;
import com.mapper.ViewShowMapper;
import com.service.IFindViewService;
import com.service.IInterScoreService;
import com.service.IStarCollectionService;
import com.service.IViewShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ownlove on 2019/1/27.
 */
@Component
public class ViewShowService implements IViewShowService {

    @Autowired
    private ViewShowMapper viewShowMapper;

    @Autowired
    private InterScoreMapper interScoreMapper;

    @Autowired
    private FindViewMapper findViewMapper;

    @Autowired
    private StarCollectionMapper starCollectionMapper;

//    @Autowired
//    private IFindViewService findViewService;
//
//    @Autowired
//    private IStarCollectionService starCollectionService;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public Long insert(ViewShowDao viewShowDao) {
        return viewShowMapper.insert(viewShowDao);
    }

    @Override
    public ViewShowDao get(Long id) {
        return viewShowMapper.get(id);
    }

    @Override
    public List<ViewShowDao> searchByKeyWord(String keyWord) {
        return viewShowMapper.searchByKeyWord(keyWord);
    }

    @Override
    public List<ViewShowDao> getViewShowAlreadyScoreByOthers() {
        return viewShowMapper.getViewShowAlreadyScoreByOthers();
    }

    @Override
    public List<ViewShowDao> getViewShowByUserId(String userId) {
        return viewShowMapper.getViewShowByUserId(userId);
    }

    @Transactional
    @Override
    public int delete(String viewShowId) {
        interScoreMapper.deleteByViewShowId(viewShowId);
        findViewMapper.delete(Long.valueOf(viewShowId));
        return viewShowMapper.delete(viewShowId);
    }

    @Override
    public int update(ViewShowDao viewShowDao) {
        return viewShowMapper.update(viewShowDao);
    }

    @Override
    public int wantToUpdate(ViewShowDao viewShowDao){
//        save_find_view_dao(viewShowDao);
        FindViewDao find_viewDao = new FindViewDao();
        find_viewDao.setId(viewShowDao.getId());
        find_viewDao.setCity(viewShowDao.getCity());
        find_viewDao.setMoney(viewShowDao.getMoney());
        find_viewDao.setStar(0);
        find_viewDao.setTitle(viewShowDao.getTitle());
        find_viewDao.setType(viewShowDao.getType());
        find_viewDao.setUser_id(viewShowDao.getUser_id());
        find_viewDao.setDefaultpath(viewShowDao.getDefaultpath());
        findViewMapper.insert(find_viewDao);

//        保存这些StarCollection操作
        StarCollectionDao star_collectionDao = new StarCollectionDao();
        star_collectionDao.setView_show_id(find_viewDao.getId());
        star_collectionDao.setStar(0);
        star_collectionDao.setCollection(0);
        starCollectionMapper.insert(star_collectionDao);

        return viewShowMapper.update(viewShowDao);
    }

//    /**
//     * 保证只有这里能进行FindView的Insert操作
//     * @param viewShowDao
//     */
//    private void save_find_view_dao(ViewShowDao viewShowDao) {
//
//    }
}
