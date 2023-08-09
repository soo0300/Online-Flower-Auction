import axios from 'axios';
import NoticeRow from './NoticeRow';
import { useEffect } from 'react';

const Notice = () => {

    const data = {
        no: 1,
        title: "title",
        created: '2023-01-01'
    }
    useEffect(() => {
        axios
            .get(`/api/api/admin-service/notices`)
            .then((response) => {
                console.log(response.data)
            })
        
    } , []);

    return (
        <div className='gap-24 bg-gray-20 md:h-full md:pb-0'>
            <h1 className='text-center mt-5 mb-5'>공지사항</h1>
            <div className='container  items-center justify-self-center'>
                <table className='table-auto w-100 border-collapse border-slate-500'>
                    <thead className='text-center text-xl border-bottom border-top'>
                        <tr className='h-16'>
                            <th>번호</th>
                            <th>제목</th>
                            <th>작성자</th>
                            <th>작성일</th>
                        </tr>
                    </thead>
                    <tbody className='text-center'>
                        <NoticeRow data={data}/>
                        
                        <tr className='border-bottom h-16'>
                            <td>2</td>
                            <td className='text-left'>2022년 학교별 졸업예정표(서울,경기,인천)</td>
                            <td>관리자</td>
                            <td>2022-12-09</td>
                        </tr>
                        <tr className='border-bottom h-16'>
                            <td>3</td>
                            <td className='text-left'>2021년 학교별 졸업예정표(서울,경기,인천)</td>
                            <td>관리자</td>
                            <td>2022-12-09</td>
                        </tr>
                        <tr className='border-bottom h-16'>
                            <td>4</td>
                            <td className='text-left'>2020년 학교별 졸업예정표(서울,경기,인천)</td>
                            <td>관리자</td>
                            <td>2022-12-09</td>
                        </tr>
                        <tr className='border-bottom h-16'>
                            <td>5</td>
                            <td className='text-left'>2019년 학교별 졸업예정표(서울,경기,인천)</td>
                            <td>관리자</td>
                            <td>2022-12-09</td>
                        </tr>
                    </tbody>
                </table>
            </div>

        </div>
    )
}

export default Notice;