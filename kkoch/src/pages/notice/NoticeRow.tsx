const NoticeRow = (props) => {
    
    return (
        <tr className='border-bottom h-16'>
            <td>{props.data.no}</td>
            <td className='text-left'>{props.data.title}</td>
            <td>관리자</td>
            <td>{props.data.created}</td>
        </tr>
    )
}

export default NoticeRow;