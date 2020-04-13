package com.baizhi.wyj.repository;

import com.baizhi.wyj.entity.Video;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface VideoRepository extends ElasticsearchRepository<Video,String> {
}
