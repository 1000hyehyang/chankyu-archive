package com.imchankyu.news.repository;

import com.imchankyu.news.entity.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {

    @Query("SELECT n.link FROM NewsArticle n WHERE n.link IN :links")
    List<String> findLinksByLinkIn(@Param("links") List<String> links);
}

