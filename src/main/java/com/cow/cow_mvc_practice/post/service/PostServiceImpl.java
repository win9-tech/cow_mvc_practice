package com.cow.cow_mvc_practice.post.service;

import com.cow.cow_mvc_practice.comment.repository.CommentJPARepository;
import com.cow.cow_mvc_practice.member.entity.Member;
import com.cow.cow_mvc_practice.member.repository.MemberJPARepository;
import com.cow.cow_mvc_practice.post.dto.request.CreatePostRequest;
import com.cow.cow_mvc_practice.post.dto.request.DeletePostRequest;
import com.cow.cow_mvc_practice.post.dto.response.CreatePostResponse;
import com.cow.cow_mvc_practice.post.dto.response.FindPostResponse;
import com.cow.cow_mvc_practice.post.entity.Post;
import com.cow.cow_mvc_practice.post.repository.PostJPARepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostServiceImpl implements PostService {

    private final PostJPARepository postJPARepository;
    private final MemberJPARepository memberJPARepository;
    private final CommentJPARepository commentJPARepository;

    @Override
    public CreatePostResponse create(CreatePostRequest createPostRequest) {
        Member member = findMember(createPostRequest.getId());
        Post post = createPostRequest.toEntity();
        post.addMember(member);
        postJPARepository.save(post);
        return CreatePostResponse.from(post);
    }

    @Transactional(readOnly = true)
    @Override
    public FindPostResponse find(Long postId) {
        Post post = findPost(postId);
        int commentCount = commentJPARepository.countByPostId(postId);
        return FindPostResponse.from(post, commentCount);
    }

    @Override
    public void delete(Long postId, DeletePostRequest deletePostRequest) {
        Post post = findPost(postId);
        Member member = findMember(deletePostRequest.getId());
        if (post.getMember().getId().equals(member.getId())) {
            postJPARepository.delete(post);
        }
    }

    @Override
    public List<CreatePostResponse> findAll() {
        List<Post> posts = postJPARepository.findAll();
        return posts.stream()
                .map(CreatePostResponse::from)
                .collect(Collectors.toList());
    }

    private Member findMember(Long memberId) {
        return memberJPARepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다."));
    }

    private Post findPost(Long postId) {
        return postJPARepository.findById(postId).orElseThrow(() ->
                new EntityNotFoundException("게시글이 존재하지 않습니다."));
    }
}
